package ro.webdata.normalization.timespan.ro;

import org.apache.commons.lang3.StringUtils;
import ro.webdata.echo.commons.Const;
import ro.webdata.echo.commons.File;
import ro.webdata.echo.commons.Print;
import ro.webdata.normalization.timespan.commons.EnvConst;
import ro.webdata.normalization.timespan.ro.regex.AgeRegex;
import ro.webdata.normalization.timespan.ro.regex.TimePeriodRegex;
import ro.webdata.normalization.timespan.ro.regex.UnknownRegex;
import ro.webdata.normalization.timespan.ro.regex.YearRegex;
import ro.webdata.normalization.timespan.ro.regex.date.DateRegex;
import ro.webdata.normalization.timespan.ro.regex.date.LongDateRegex;
import ro.webdata.normalization.timespan.ro.regex.date.ShortDateRegex;
import ro.webdata.normalization.timespan.ro.regex.imprecise.DatelessRegex;
import ro.webdata.normalization.timespan.ro.regex.imprecise.InaccurateYearRegex;
import ro.webdata.parser.xml.lido.core.ParserDAO;
import ro.webdata.parser.xml.lido.core.ParserDAOImpl;
import ro.webdata.parser.xml.lido.core.leaf.descriptiveMetadata.DescriptiveMetadata;
import ro.webdata.parser.xml.lido.core.leaf.displayDate.DisplayDate;
import ro.webdata.parser.xml.lido.core.leaf.event.Event;
import ro.webdata.parser.xml.lido.core.leaf.eventDate.EventDate;
import ro.webdata.parser.xml.lido.core.leaf.lido.Lido;
import ro.webdata.parser.xml.lido.core.set.eventSet.EventSet;
import ro.webdata.parser.xml.lido.core.wrap.lidoWrap.LidoWrap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LidoXmlTimespanAnalysis {
    private LidoXmlTimespanAnalysis() {}

    private static final ParserDAO parserDAO = new ParserDAOImpl();
    private static final String OPERATION_END = "----- END -----";
    private static final String OPERATION_START = "----- START -----";

    /**
     * Extract all time expressions from LIDO files<br/>
     * <b>Used in the analysis process</b>
     * @param outputFullPath The full path of the output file
     * @param inputPath The path to input LIDO files
     * @param excludedFiles List of excluded files (E.g.: "demo.xml")
     */
    public static void writeAll(String inputPath, String outputFullPath, ArrayList<String> excludedFiles) {
        Print.operation(OPERATION_START, EnvConst.SHOULD_PRINT);
        Print.operation("LidoXmlTimespanAnalysis.writeAll is in progress...", EnvConst.SHOULD_PRINT);

        HashMap<String, ArrayList<String>> timespanMap = extractTimespan(inputPath, excludedFiles);

        for (Map.Entry<String, ArrayList<String>> entry : timespanMap.entrySet()) {
            String eventType = entry.getKey();
            String newOutputFullPath = eventType != null
                    ? appendFileSuffix(outputFullPath, eventType)
                    : outputFullPath;

            ArrayList<String> list = entry.getValue();
            write(list, newOutputFullPath);
        }

        ArrayList<String> consolidatedTimespanMap = consolidateTimespanMap(timespanMap);
        write(consolidatedTimespanMap, outputFullPath);

        Print.operation(OPERATION_END, EnvConst.SHOULD_PRINT);
    }

    /**
     * Extract all unique time expressions from LIDO files<br/>
     * <b>Used in the analysis process</b>
     * @param outputFullPath The full path of the output file
     * @param inputPath The path to input LIDO files
     * @param excludedFiles List of excluded files (E.g.: "demo.xml")
     */
    public static void writeUnique(String inputPath, String outputFullPath, ArrayList<String> excludedFiles) {
        Print.operation(OPERATION_START, EnvConst.SHOULD_PRINT);
        Print.operation("LidoXmlTimespanAnalysis.writeUnique is in progress...", EnvConst.SHOULD_PRINT);

        HashMap<String, ArrayList<String>> timespanMap = extractTimespan(inputPath, excludedFiles);

        for (Map.Entry<String, ArrayList<String>> entry : timespanMap.entrySet()) {
            String eventType = entry.getKey();
            String newOutputFullPath = eventType != null
                    ? appendFileSuffix(outputFullPath, eventType)
                    : outputFullPath;

            Set<String> set = new TreeSet<>(entry.getValue());
            write(new ArrayList<>(set), newOutputFullPath);
        }

        Set<String> set = new TreeSet<>(consolidateTimespanMap(timespanMap));
        write(new ArrayList<>(set), outputFullPath);

        Print.operation(OPERATION_END, EnvConst.SHOULD_PRINT);
    }

    private static void write(ArrayList<String> timespanList, String outputFullPath) {
        File.write(timespanList, outputFullPath, false);

        ArrayList<String> timeExpressions = toTimeExpressions(timespanList);
        File.write(timeExpressions, generateCsvFilePath(outputFullPath), false);
    }

    /**
     * Replace the extension of the input filePath with **File.EXTENSION_CSV**
     * @param filePath The full path
     * @return The new path
     */
    private static String generateCsvFilePath(String filePath) {
        return filePath.replaceAll("\\.[a-zA-Z]*", File.EXTENSION_SEPARATOR + File.EXTENSION_CSV);
    }

    /**
     * Append the filename suffix to the input filePath.
     * E.g.: /usr/local/my_file.txt => /usr/local/my_file_suffix.txt
     *
     * @param filePath The full path
     * @param suffix A word added at the end of the target word
     * @return The new path
     */
    private static String appendFileSuffix(String filePath, String suffix) {
        String SUFFIX = suffix == null ? "" : suffix.toLowerCase();
        String newFilePath = filePath.replaceAll("\\.[a-zA-Z]*", Const.UNDERSCORE_PLACEHOLDER + SUFFIX);
        String extension = filePath.replaceAll(".*\\.", "");

        return newFilePath + File.EXTENSION_SEPARATOR + extension;
    }

    public static void check(String filePath) {
        // Order is crucial !!!
        String[] list = {
                UnknownRegex.UNKNOWN,

                DateRegex.DATE_DMY_INTERVAL,
                DateRegex.DATE_YMD_INTERVAL,
                ShortDateRegex.DATE_MY_INTERVAL,
                DateRegex.DATE_DMY_OPTIONS,
                DateRegex.DATE_YMD_OPTIONS,
                ShortDateRegex.DATE_MY_OPTIONS,
                LongDateRegex.LONG_DATE_OPTIONS,

                TimePeriodRegex.CENTURY_INTERVAL,
                TimePeriodRegex.CENTURY_OPTIONS,
                TimePeriodRegex.MILLENNIUM_INTERVAL,
                TimePeriodRegex.MILLENNIUM_OPTIONS,
                TimePeriodRegex.OTHER_CENTURY_ROMAN_INTERVAL,
                TimePeriodRegex.OTHER_CENTURY_ROMAN_OPTIONS,
                AgeRegex.PLEISTOCENE_AGE,
                AgeRegex.MESOLITHIC_AGE,
                AgeRegex.CHALCOLITHIC_AGE,
                AgeRegex.NEOLITHIC_AGE,
                AgeRegex.BRONZE_AGE,
                AgeRegex.MIDDLE_AGES,
                AgeRegex.MODERN_AGES,
                AgeRegex.HALLSTATT_CULTURE,
                AgeRegex.AURIGNACIAN_CULTURE,
                AgeRegex.PTOLEMAIC_DYNASTY,
                AgeRegex.ROMAN_EMPIRE_AGE,
                AgeRegex.NERVA_ANTONINE_DYNASTY,
                AgeRegex.RENAISSANCE,
                AgeRegex.FRENCH_CONSULATE_AGE,
                AgeRegex.WW_I_PERIOD,
                AgeRegex.INTERWAR_PERIOD,
                AgeRegex.WW_II_PERIOD,

                DatelessRegex.DATELESS,
                InaccurateYearRegex.AFTER_INTERVAL,
                InaccurateYearRegex.BEFORE_INTERVAL,
                InaccurateYearRegex.APPROX_AGES_INTERVAL,
                InaccurateYearRegex.AFTER,
                InaccurateYearRegex.BEFORE,
                InaccurateYearRegex.APPROX_AGES_OPTIONS,

                YearRegex.YEAR_INTERVAL,
                YearRegex.YEAR_3_4_DIGITS_SPECIAL_INTERVAL,
                YearRegex.YEAR_OPTIONS,
                YearRegex.UNKNOWN_YEARS
        };

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String readLine;

            while ((readLine = br.readLine()) != null) {
                if (readLine.length() > 0) {
                    boolean check = isMatching(readLine, list);
                    if (!check)
                        System.out.println(readLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printUnknownTimeExpressions(String filePath) {
        // Order is crucial !!!
        String[] list = {
                UnknownRegex.UNKNOWN,

                AgeRegex.PLEISTOCENE_AGE,
                AgeRegex.MESOLITHIC_AGE,
                AgeRegex.CHALCOLITHIC_AGE,
                AgeRegex.NEOLITHIC_AGE,
                AgeRegex.BRONZE_AGE,
                AgeRegex.MIDDLE_AGES,
                AgeRegex.MODERN_AGES,
                AgeRegex.HALLSTATT_CULTURE,
                AgeRegex.AURIGNACIAN_CULTURE,
                AgeRegex.PTOLEMAIC_DYNASTY,
                AgeRegex.ROMAN_EMPIRE_AGE,
                AgeRegex.NERVA_ANTONINE_DYNASTY,
                AgeRegex.RENAISSANCE,
                AgeRegex.FRENCH_CONSULATE_AGE,
                AgeRegex.WW_I_PERIOD,
                AgeRegex.INTERWAR_PERIOD,
                AgeRegex.WW_II_PERIOD,

                DatelessRegex.DATELESS,
                InaccurateYearRegex.AFTER_INTERVAL,
                InaccurateYearRegex.BEFORE_INTERVAL,
                InaccurateYearRegex.APPROX_AGES_INTERVAL,
                InaccurateYearRegex.AFTER,
                InaccurateYearRegex.BEFORE,
                InaccurateYearRegex.APPROX_AGES_OPTIONS
        };

        try {
            int count = 0;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String readLine;

            while ((readLine = br.readLine()) != null) {
                if (readLine.length() > 0) {
                    boolean check = isMatching(readLine, list);
                    if (check)
                        count++;
                }
            }

            System.out.println("unknown time expressions: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> toTimeExpressions(ArrayList<String> strTimeExpressions) {
        ArrayList<String> timeExpressions = new ArrayList<>();

        for (String str : strTimeExpressions) {
            TimeExpression timeExpression = new TimeExpression(str, "|");
            timeExpressions.add(timeExpression.toString());
        }

        return timeExpressions;
    }

    private static ArrayList<String> consolidateTimespanMap(HashMap<String, ArrayList<String>> timespanMap) {
        ArrayList<String> consolidatedList = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> entry : timespanMap.entrySet()) {
            consolidatedList.addAll(entry.getValue());
        }

        Collections.sort(consolidatedList);
        return consolidatedList;
    }

    /**
     * Extract all timespan to a sorted list
     * @param inputPath The path to input LIDO files
     * @param excludedFiles List of excluded files (E.g.: "demo.xml")
     */
    private static HashMap<String, ArrayList<String>> extractTimespan(String inputPath, ArrayList<String> excludedFiles) {
        ArrayList<String> excludedList = excludedFiles != null
                ? excludedFiles
                : new ArrayList<>();
        HashMap<String, ArrayList<String>> timespanMap = new HashMap<>();
        java.io.File file = new java.io.File(inputPath);
        String[] fileList = file.list();

        if (fileList != null) {
            for (String fileName : fileList) {
                if (
                        fileName.contains(File.EXTENSION_SEPARATOR + File.EXTENSION_XML) &&
                                !excludedList.contains(fileName)
                ) {
                    String filePath = inputPath + File.FILE_SEPARATOR + fileName;
                    addTimespan(filePath, timespanMap);
                }
            }
        }

        for (Map.Entry<String, ArrayList<String>> entry : timespanMap.entrySet()) {
            Collections.sort(entry.getValue());
        }

        return timespanMap;
    }

    /**
     * Add timespan to the provided list
     * @param filePath The path to the LIDO file
     * @param timespanMap The map containing the list of time expressions per event type
     */
    private static void addTimespan(String filePath, HashMap<String, ArrayList<String>> timespanMap) {
        LidoWrap lidoWrap = parserDAO.parseLidoFile(filePath);
        ArrayList<Lido> lidoList = lidoWrap.getLidoList();

        for (Lido lido : lidoList) {
            ArrayList<DescriptiveMetadata> descriptiveMetadataList = lido.getDescriptiveMetadata();

            for (DescriptiveMetadata descriptiveMetadata : descriptiveMetadataList) {
                ArrayList<EventSet> eventSetList = descriptiveMetadata.getEventWrap().getEventSet();
                addEventDateTimespan(eventSetList, timespanMap);
            }
        }
    }

    /**
     * Add event related timespan to the provided list
     * @param eventSetList The list of EventSet items
     * @param timespanMap The map containing the list of time expressions per event type
     */
    private static void addEventDateTimespan(ArrayList<EventSet> eventSetList, HashMap<String, ArrayList<String>> timespanMap) {
        for (EventSet eventSet : eventSetList) {
            Event event = eventSet.getEvent();
            EventDate eventDate = event.getEventDate();
            List<String> termList = event
                    .getEventType()
                    .getTerm()
                    .stream().map(term -> term.getText().trim())
                    .collect(Collectors.toList());

            if (eventDate != null) {
                ArrayList<DisplayDate> displayDateList = eventDate.getDisplayDate();

                for (DisplayDate displayDate : displayDateList) {
                    String timespan = displayDate.getText().toLowerCase();

                    for (String eventType : termList) {
                        if (timespanMap.containsKey(eventType)) {
                            timespanMap.get(eventType).add(timespan);
                        } else {
                            ArrayList<String> arr = new ArrayList<>() {{
                                add(timespan);
                            }};
                            timespanMap.put(eventType, arr);
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if the input value is matching at least one of the regexes from the list
     * @param value The input value
     * @param regexList The matching regex list
     * @return true/false
     */
    private static boolean isMatching(String value, String[] regexList) {
        for (String regex : regexList) {
            if (isMatching(value, regex))
                return true;
        }

        return false;
    }

    /**
     * Check if the input value is matching the regex
     * @param value The input value
     * @param regex The matching regex
     * @return true/false
     */
    private static boolean isMatching(String value, String regex) {
        String preparedValue = StringUtils.stripAccents(value);
        preparedValue = TimeSanitizeUtils.sanitizeValue(preparedValue, regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(preparedValue);
        return matcher.find();
    }
}
