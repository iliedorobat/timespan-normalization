package ro.webdata.normalization.timespan.ro;

import ro.webdata.echo.commons.Const;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

public class TimePeriodUtils {
    private static final String SPECIAL_CHARS_REGEX = "[\\.,;\\?!]*\\s*";
    private static final String[] REGEX_LIST = {
            CASE_INSENSITIVE + START_END,
            CASE_INSENSITIVE + FIRST_HALF,
            CASE_INSENSITIVE + SECOND_HALF,
            CASE_INSENSITIVE + MIDDLE_OF,
            CASE_INSENSITIVE + FIRST_QUARTER,
            CASE_INSENSITIVE + SECOND_QUARTER,
            CASE_INSENSITIVE + THIRD_QUARTER,
            CASE_INSENSITIVE + FORTH_QUARTER,
            CASE_INSENSITIVE + CENTURY_LABEL,
            CASE_INSENSITIVE + MILLENNIUM_LABEL,
            CASE_INSENSITIVE + YEAR_LABEL,
            CASE_INSENSITIVE + AGES_GROUP_SUFFIX,
            CASE_INSENSITIVE + ARTICLE_AL,
            SPECIAL_CHARS_REGEX
    };

    /**
     * Get the time period value from the input value
     * @param value The initial value
     * @return The prepared value (E.g.: i, ii, iii, iv etc.)
     */
    public static String sanitizeTimePeriod(String value) {
        String preparedValue = value;
        List<String> groups = getGroupList(preparedValue);

        for (int i = groups.size() - 1; i >= 0; i--) {
            String group = groups.get(i);

            if (!group.isEmpty()) {
                // E.g.: "3/4. sec. 19", "4/4. xviii - 1/4. xix", "sf sec.xix - mijl. sec. xx"
                if (specialCharsOnly(group) && group.contains(".")) {
                    group = group.replaceAll("\\.", "");
                    preparedValue = preparedValue.replaceAll("\\.", Const.EMPTY_VALUE_PLACEHOLDER);
                }

                preparedValue = preparedValue.replaceAll(group, Const.EMPTY_VALUE_PLACEHOLDER);
            }
        }

        preparedValue = preparedValue.replaceAll("\\s*", Const.EMPTY_VALUE_PLACEHOLDER);

        return preparedValue;
    }

    private static List<String> getGroupList(String value) {
        Set<String> set = new HashSet<>();

        for (String regex : REGEX_LIST) {
            Pattern suffixPattern = Pattern.compile(regex);
            Matcher suffixMatcher = suffixPattern.matcher(value);

            while (suffixMatcher.find()) {
                String group = suffixMatcher.group();

                if (!group.isEmpty()) {
                    if (!isSpace(group)) {
                        set.add(group.trim());
                    }
                }
            }
        }

        return set.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
    }

    private static boolean isSpace(String regex) {
        return regex.matches("\\s\\s*");
    }

    private static boolean specialCharsOnly(String regex) {
        for (int i = 0; i < regex.length(); i++) {
            char item = regex.charAt(i);

            if (!SPECIAL_CHARS_REGEX.contains(String.valueOf(item))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Transform a time period into number.
     * @param timePeriod The original time period (E.g.: 'i', '5' etc.)
     * @param isDate Flag indicating if the input timePeriod is a date (E.g.: "01.10.1929", "1709, decembrie 24", etc.)
     * @return The number that represents the time period (E.g.: 5, 9 etc.)
     */
    public static Integer timePeriodToNumber(String timePeriod, boolean isDate) {
        // E.g.: "1/2 mil. 5 - sec. i al mil. 4 a.chr."
        if (timePeriod == null || timePeriod.trim().isEmpty()) {
            return null;
        }

        String clearedTimePeriod = isDate ? TimeUtils.clearDate(timePeriod) : timePeriod;
        clearedTimePeriod = TimeUtils.clearChristumNotation(clearedTimePeriod);

        try {
            return Integer.parseInt(clearedTimePeriod);
        } catch (Exception e) {
            try {
                return TimeUtils.romanToInt(clearedTimePeriod);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public static Integer getStartTime(String[] intervalValues, String eraStart, boolean isDate) {
        Integer first = TimePeriodUtils.timePeriodToNumber(intervalValues[0], isDate);
        Integer second = TimePeriodUtils.timePeriodToNumber(intervalValues[1], isDate);

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

    public static Integer getEndTime(String[] intervalValues, String eraStart, boolean isDate) {
        Integer first = TimePeriodUtils.timePeriodToNumber(intervalValues[0], isDate);
        Integer second = TimePeriodUtils.timePeriodToNumber(intervalValues[1], isDate);

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
