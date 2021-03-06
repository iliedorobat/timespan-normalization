package main.java.ro.webdata.normalization.timespan.ro;

import main.java.ro.webdata.normalization.timespan.commons.EnvConst;
import org.apache.commons.lang3.StringUtils;
import ro.webdata.echo.commons.Const;
import ro.webdata.echo.commons.File;
import main.java.ro.webdata.normalization.timespan.ro.regex.AgeRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.TimePeriodRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.UnknownRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.YearRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.date.DateRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.date.LongDateRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.date.ShortDateRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.imprecise.DatelessRegex;
import main.java.ro.webdata.normalization.timespan.ro.regex.imprecise.InaccurateYearRegex;
import ro.webdata.echo.commons.Print;
import ro.webdata.parser.xml.lido.common.Constants;
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
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LidoXmlTimespanAnalysis {
    private LidoXmlTimespanAnalysis() {}

    private static ParserDAO parserDAO = new ParserDAOImpl();

    /**
     * Extract all timespan values from LIDO files<br/>
     * <b>Used in the analysis process</b>
     * @param fileNames The list of LIDO file name
     * @param outputFullPath The full path for the output file
     * @param inputPath The path to input LIDO files
     */
    public static void writeAll(String inputPath, String[] fileNames, String outputFullPath) {
        Print.operation(Const.OPERATION_START, EnvConst.SHOULD_PRINT);

        StringWriter writer = new StringWriter();
        ArrayList<String> list = extractTimespan(fileNames, inputPath);

        for (String string : list) {
            writer.append(string).append("\n");
        }

        File.write(writer, outputFullPath);

        Print.operation(Const.OPERATION_END, EnvConst.SHOULD_PRINT);
    }

    /**
     * Extract all unique timespan values from LIDO files<br/>
     * <b>Used in the analysis process</b>
     * @param fileNames The list of LIDO file name
     * @param outputFullPath The full path for the output file
     * @param inputPath The path to input LIDO files
     */
    public static void writeUnique(String inputPath, String[] fileNames, String outputFullPath) {
        Print.operation(Const.OPERATION_START, EnvConst.SHOULD_PRINT);

        StringWriter writer = new StringWriter();
        ArrayList<String> list = extractTimespan(fileNames, inputPath);
        Set<String> set = new TreeSet<>(list);

        for (String string : set) {
            writer.append(string).append("\n");
        }

        File.write(writer, outputFullPath);

        Print.operation(Const.OPERATION_END, EnvConst.SHOULD_PRINT);
    }

    public static void check(String filePath) {
        // The order is crucial !!!
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

    /**
     * Extract all timespan to a sorted list
     * @param fileNames The list of paths to LIDO files
     * @param inputPath The path to input LIDO files
     */
    private static ArrayList<String> extractTimespan(String[] fileNames, String inputPath) {
        ArrayList<String> list = new ArrayList<>();

        for (int count = 0; count < fileNames.length; count++) {
            String filePath = inputPath
                    + Constants.FILE_SEPARATOR + fileNames[count]
                    + File.EXTENSION_SEPARATOR + File.EXTENSION_XML;
            addTimespan(filePath, list);
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

        for (int i = 0; i < lidoWrap.getLido().size(); i++) {
            Lido lido = lidoWrap.getLido().get(i);
            ArrayList<DescriptiveMetadata> descriptiveMetadataList = lido.getDescriptiveMetadata();

            for (int j = 0; j < descriptiveMetadataList.size(); j++) {
                DescriptiveMetadata descriptiveMetadata = descriptiveMetadataList.get(j);
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
        for (int i = 0; i < eventSetList.size(); i++) {
            EventSet eventSet = eventSetList.get(i);
            EventDate eventDate = eventSet.getEvent().getEventDate();

            if (eventDate != null) {
                ArrayList<DisplayDate> displayDateList = eventDate.getDisplayDate();

                for (int k = 0; k < displayDateList.size(); k++) {
                    DisplayDate displayDate = displayDateList.get(k);
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
        boolean output = false;

        for (int i = 0; i < regexList.length; i++) {
            output = output || isMatching(value, regexList[i]);
        }

        return output;
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

        while (matcher.find()) {
            return true;
        }

        return false;
    }
}
