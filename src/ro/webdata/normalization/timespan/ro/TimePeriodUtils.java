package ro.webdata.normalization.timespan.ro;

import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimePeriodUtils {
    private static final String[] REGEX_LIST = {
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.CENTURY_NOTATION,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.MILLENNIUM_NOTATION,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.FIRST_HALF,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.SECOND_HALF,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.MIDDLE_OF,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.FIRST_QUARTER,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.SECOND_QUARTER,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.THIRD_QUARTER,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.FORTH_QUARTER,
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.AGES_GROUP_SUFFIX,
            "[\\.,;\\?!\\p{Space}]*"
    };

    /**
     * Get the time period value from the input value
     * @param value The initial value
     * @return The prepared value (E.g.: i, ii, iii, iv etc.)
     */
    public static String sanitizeTimePeriod(String value) {
        String preparedValue = value;

        for (String regex : REGEX_LIST) {
            Pattern suffixPattern = Pattern.compile(regex);
            Matcher suffixMatcher = suffixPattern.matcher(preparedValue);

            while (suffixMatcher.find()) {
                String group = suffixMatcher.group();
                preparedValue = preparedValue.replaceAll(group, Const.EMPTY_VALUE_PLACEHOLDER);
            }
        }

        return preparedValue;
    }

    /**
     * Transform a time period into number.
     * @param timePeriod The original time period (E.g.: 'i', '5' etc.)
     * @return The number that represents the time period (E.g.: 5, 9 etc.)
     */
    public static Integer timePeriodToNumber(String timePeriod) {
        Integer value = null;

        try {
            value = Integer.parseInt(timePeriod);
        } catch (Exception e) {
            value = TimeUtils.romanToInt(timePeriod);
        }

        return value;
    }
}
