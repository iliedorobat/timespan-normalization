package ro.webdata.normalization.timespan.ro;

import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimePeriodUtils {
    private static final String[] REGEX_LIST = {
            TimespanRegex.CASE_INSENSITIVE + TimespanRegex.START_END,
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
        String clearedTimePeriod = TimeUtils.clearInaccurateDate(timePeriod);
        clearedTimePeriod = TimeUtils.clearChristumNotation(clearedTimePeriod);

        try {
            value = Integer.parseInt(clearedTimePeriod);
        } catch (Exception e) {
            value = TimeUtils.romanToInt(clearedTimePeriod);
        }

        return value;
    }

    public static Integer getStartTime(String[] intervalValues, String eraStart) {
        Integer first = TimePeriodUtils.timePeriodToNumber(intervalValues[0]);
        Integer second = TimePeriodUtils.timePeriodToNumber(intervalValues[1]);

        // E.g.: "prima jum. a sec. xxxxiv - xxxv a.ch."
        // "xxxxiv" is an invalid roman numeral
        if (first == null || second == null) {
            return null;
        }

        boolean isAD = eraStart != null && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER);
        if (isAD && first > second) {
            return second;
        }

        // E.g. "sec. ii a.chr. - sec. i p.chr."
        return first;
    }

    public static Integer getEndTime(String[] intervalValues, String eraStart) {
        Integer first = TimePeriodUtils.timePeriodToNumber(intervalValues[0]);
        Integer second = TimePeriodUtils.timePeriodToNumber(intervalValues[1]);

        // E.g.: "prima jum. a sec. xxxxiv - xxxv a.ch."
        // "xxxxiv" is an invalid roman numeral
        if (first == null || second == null) {
            return null;
        }

        boolean isAD = eraStart != null && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER);
        if (isAD && first > second) {
            return first;
        }

        // E.g. "sec. ii a.chr. - sec. i p.chr."
        return second;
    }
}
