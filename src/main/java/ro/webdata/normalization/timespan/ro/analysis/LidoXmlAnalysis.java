package ro.webdata.normalization.timespan.ro.analysis;

import ro.webdata.normalization.timespan.ro.TimeSanitizeUtils;
import ro.webdata.normalization.timespan.ro.regex.AgeRegex;
import ro.webdata.normalization.timespan.ro.regex.TimePeriodRegex;
import ro.webdata.normalization.timespan.ro.regex.UnknownRegex;
import ro.webdata.normalization.timespan.ro.regex.YearRegex;
import ro.webdata.normalization.timespan.ro.regex.date.DateRegex;
import ro.webdata.normalization.timespan.ro.regex.date.LongDateRegex;
import ro.webdata.normalization.timespan.ro.regex.date.ShortDateRegex;
import ro.webdata.normalization.timespan.ro.regex.imprecise.DatelessRegex;
import ro.webdata.normalization.timespan.ro.regex.imprecise.InaccurateYearRegex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LidoXmlAnalysis {
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

                TimePeriodRegex.CENTURY_INTERVAL_BASE,
                TimePeriodRegex.CENTURY_OPTIONS,
                TimePeriodRegex.MILLENNIUM_INTERVAL_BASE,
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

                YearRegex.YEAR_INTERVAL_BASE,
                YearRegex.YEAR_INTERVAL_PREFIXED,
                YearRegex.YEAR_3_4_DIGITS_SPECIAL_INTERVAL,
                YearRegex.YEAR_OPTIONS,
                YearRegex.UNKNOWN_YEARS
        };

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String readLine;

            while ((readLine = br.readLine()) != null) {
                if (!readLine.isEmpty()) {
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
                if (!readLine.isEmpty()) {
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
        String preparedValue = TimeSanitizeUtils.clearJunks(value, regex);
        preparedValue = TimeSanitizeUtils.sanitizeValue(preparedValue);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(preparedValue);
        return matcher.find();
    }
}
