package main.java.ro.webdata.normalization.timespan.ro;

import main.java.ro.webdata.normalization.timespan.commons.EnvConst;
import main.java.ro.webdata.normalization.timespan.ro.regex.AgeRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.TimePeriodRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.UnknownRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.YearRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.date.DateRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.date.LongDateRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.date.ShortDateRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.imprecise.DatelessRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.imprecise.InaccurateYearRegex;
import org.apache.commons.lang3.StringUtils;
import ro.webdata.echo.commons.File;
import ro.webdata.echo.commons.Print;
import ro.webdata.parser.xml.lido.core.ParserDAO;
import ro.webdata.parser.xml.lido.core.ParserDAOImpl;
import ro.webdata.parser.xml.lido.core.leaf.descriptiveMetadata.DescriptiveMetadata;
import ro.webdata.parser.xml.lido.core.leaf.displayDate.DisplayDate;
import ro.webdata.parser.xml.lido.core.leaf.eventDate.EventDate;
import ro.webdata.parser.xml.lido.core.leaf.lido.Lido;
import ro.webdata.parser.xml.lido.core.set.eventSet.EventSet;
import ro.webdata.parser.xml.lido.core.wrap.lidoWrap.LidoWrap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LidoXmlTimespanAnalysis {
    private LidoXmlTimespanAnalysis() {}

    private static final ParserDAO parserDAO = new ParserDAOImpl();
    private static final String OPERATION_END = "----- END -----";
    private static final String OPERATION_START = "----- START -----";

    /**
     * Extract all time expressions from LIDO files<br/>
     * <b>Used in the analysis process</b>
     * @param outputFullPath The full path for the output file
     * @param inputPath The path to input LIDO files
     */
    public static void writeAll(String inputPath, String outputFullPath) {
        Print.operation(OPERATION_START, EnvConst.SHOULD_PRINT);
        Print.operation("LidoXmlTimespanAnalysis.writeAll is in progress...", EnvConst.SHOULD_PRINT);

        ArrayList<String> list = extractTimespan(inputPath);
        File.write(list, outputFullPath, false);

        Print.operation(OPERATION_END, EnvConst.SHOULD_PRINT);
    }

    /**
     * Extract all unique time expressions from LIDO files<br/>
     * <b>Used in the analysis process</b>
     * @param outputFullPath The full path for the output file
     * @param inputPath The path to input LIDO files
     */
    public static void writeUnique(String inputPath, String outputFullPath) {
        Print.operation(OPERATION_START, EnvConst.SHOULD_PRINT);
        Print.operation("LidoXmlTimespanAnalysis.writeUnique is in progress...", EnvConst.SHOULD_PRINT);

        ArrayList<String> list = extractTimespan(inputPath);
        Set<String> set = new TreeSet<>(list);
        File.write(new ArrayList<>(set), outputFullPath, false);

        Print.operation(OPERATION_END, EnvConst.SHOULD_PRINT);
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

                YearRegex.YEAR_3_4_DIGITS_INTERVAL,
                YearRegex.YEAR_3_4_DIGITS_SPECIAL_INTERVAL,
                YearRegex.YEAR_3_4_DIGITS_OPTIONS,
                YearRegex.YEAR_2_DIGITS_INTERVAL,
                YearRegex.YEAR_2_DIGITS_OPTIONS,
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

    /**
     * Extract all timespan to a sorted list
     * @param inputPath The path to input LIDO files
     */
    private static ArrayList<String> extractTimespan(String inputPath) {
        ArrayList<String> list = new ArrayList<>();
        java.io.File file = new java.io.File(inputPath);
        String[] fileList = file.list();

        if (fileList != null) {
            for (String fileName : fileList) {
                if (fileName.contains(File.EXTENSION_SEPARATOR + File.EXTENSION_XML)) {
                    String filePath = inputPath + File.FILE_SEPARATOR + fileName;
                    addTimespan(filePath, list);
                }
            }
        }

        Collections.sort(list);
        return list;
    }

    /**
     * Add timespan to the provided list
     * @param filePath The path to the LIDO file
     * @param list The related list
     */
    private static void addTimespan(String filePath, ArrayList<String> list) {
        LidoWrap lidoWrap = parserDAO.parseLidoFile(filePath);
        ArrayList<Lido> lidoList = lidoWrap.getLido();

        for (Lido lido : lidoList) {
            ArrayList<DescriptiveMetadata> descriptiveMetadataList = lido.getDescriptiveMetadata();

            for (DescriptiveMetadata descriptiveMetadata : descriptiveMetadataList) {
                ArrayList<EventSet> eventSetList = descriptiveMetadata.getEventWrap().getEventSet();
                addEventDateTimespan(eventSetList, list);
            }
        }
    }

    /**
     * Add event related timespan to the provided list
     * @param eventSetList The list of EventSet items
     * @param list The related list
     */
    private static void addEventDateTimespan(ArrayList<EventSet> eventSetList, ArrayList<String> list) {
        for (EventSet eventSet : eventSetList) {
            EventDate eventDate = eventSet.getEvent().getEventDate();

            if (eventDate != null) {
                ArrayList<DisplayDate> displayDateList = eventDate.getDisplayDate();

                for (DisplayDate displayDate : displayDateList) {
                    String timespan = displayDate.getText().toLowerCase();
                    list.add(timespan);
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
