package ro.webdata.normalization.timespan.ro.regex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

public class YearRegex {
    private YearRegex() {}

    private static final String BRACKETS_START = "[\\[\\(]";
    private static final String BRACKETS_END = "[\\]\\)]";

    private static final String PREFIX_CONSTRUCTIONS =
            "lunile" + REGEX_OR + "luni(i|[aă]|lor)?" + REGEX_OR + "lun[aă]" + REGEX_OR
            + "s[aă]pt[aă]m[aâ]ni(i|le|lor)?" + REGEX_OR + "s[aă]pt[aă]m[aâ]n[aă]" + REGEX_OR
            + "zile(le|lor)?" + REGEX_OR + "ziu[aă]" + REGEX_OR + "zi" + REGEX_OR
            + "ore(i|le|lor)?" + REGEX_OR + "or[aă]" + REGEX_OR
            + "minute(le|lor)?" + REGEX_OR + "minut(ului|ul)?" + REGEX_OR
            + "secunde(i|le|lor)?" + REGEX_OR + "secund[aă]" + REGEX_OR
            + "milisecunde(i|le|lor)?" + REGEX_OR + "milisecund[aă]";
    private static final String POSTFIX_CONSTRUCTIONS = "ani(lor)?" + REGEX_OR + "an(ului|ul)?" + REGEX_OR + PREFIX_CONSTRUCTIONS;

    // Prevents matching patterns like "luna 38", "ziua 10", etc.
    private static final String EXCLUDED_PREFIX = "(?<!(?:" + PREFIX_CONSTRUCTIONS + ")\\s)";
    // Prevents matching patterns like "38 de ani", "38 ani", etc.
    private static final String EXCLUDED_POSTFIX = "(?!\\s*(?:de\\s*)?(?:" + POSTFIX_CONSTRUCTIONS + "))";

    public static final String YEAR_OR_SEPARATOR =
            "("
                    + "\\s*"
                    + "("
                    // E.g.: "110/109 a. chr."; "anul 13=1800/1801"
                    + "/" + REGEX_OR
                    // E.g.: "112 sau 111 î.chr."
                    + "sau"
                    + ")"
                    + "\\s*"
                    + ")";

    // E.g.: "15.000"; "[1]989"; "(19)89"; "1989"; "(19)89 martie"
    public static final String YEAR_GROUP_1 =
            "(?:"
                + "\\d{1,3}\\.(?:\\d{3}\\.?)+"
                + REGEX_OR
                + BRACKETS_START + "\\d{1,4}" + BRACKETS_END + "\\d{0,3}"
                + REGEX_OR
                + "\\d?" + BRACKETS_START + "\\d{1,3}" + BRACKETS_END + "\\d{0,2}"
                + REGEX_OR
                + "\\d{3,4}"
            + ")" + MONTHS + "?";
    // Prevents matching day-month expressions like "23 martie" and hour-minutes expressions like "18.05"
    public static final String YEAR_GROUP_2 = "(?:\\d{2,3})" + "(?!(\\s*" + MONTHS + ")|(\\.\\d))\\b";
    public static final String YEAR_GROUP = "(?<![\\d\\.])" + "(" + YEAR_GROUP_1 + REGEX_OR + YEAR_GROUP_2 + ")" + EXCLUDED_POSTFIX;
    public static final String YEAR_NOTATION = EXCLUDED_PREFIX + YEAR_GROUP + AD_BC_OPTIONAL;

    public static final String YEAR = CASE_INSENSITIVE +
            "("
                + TEXT_START
                + "(" + YEAR_LABEL + ")?" + YEAR_NOTATION
                + TEXT_END
            + ")";
    private static final String YEAR_FIRST_HALF = FIRST_HALF + REGEX_PUNCTUATION_UNLIMITED + YEAR;
    private static final String YEAR_SECOND_HALF = SECOND_HALF + REGEX_PUNCTUATION_UNLIMITED + YEAR;
    private static final String YEAR_MIDDLE_OF = MIDDLE_OF + REGEX_PUNCTUATION_UNLIMITED + YEAR;
    private static final String YEAR_FIRST_QUARTER = FIRST_QUARTER + REGEX_PUNCTUATION_UNLIMITED + YEAR;
    private static final String YEAR_SECOND_QUARTER = SECOND_QUARTER + REGEX_PUNCTUATION_UNLIMITED + YEAR;
    private static final String YEAR_THIRD_QUARTER = THIRD_QUARTER + REGEX_PUNCTUATION_UNLIMITED + YEAR;
    private static final String YEAR_FORTH_QUARTER = FORTH_QUARTER + REGEX_PUNCTUATION_UNLIMITED + YEAR;
    public static final String YEAR_OPTIONS = CASE_INSENSITIVE
            + "("
                + "(" + YEAR_FIRST_HALF + ")" + REGEX_OR
                + "(" + YEAR_SECOND_HALF + ")" + REGEX_OR
                + "(" + YEAR_MIDDLE_OF + ")" + REGEX_OR
                + "(" + YEAR_FIRST_QUARTER + ")" + REGEX_OR
                + "(" + YEAR_SECOND_QUARTER + ")" + REGEX_OR
                + "(" + YEAR_THIRD_QUARTER + ")" + REGEX_OR
                + "(" + YEAR_FORTH_QUARTER + ")" + REGEX_OR
                + "(" + YEAR + ")"
            + ")";

    public static final String YEAR_INTERVAL_BASE = CASE_INSENSITIVE + TEXT_START
            + "("
                + YEAR_OPTIONS
                + REGEX_INTERVAL_DELIMITER
                + YEAR_OPTIONS
            + ")";

    public static final String YEAR_INTERVAL_PREFIXED =  CASE_INSENSITIVE + TEXT_START
            + "("
                + REGEX_INTERVAL_PREFIX
                + YEAR_OPTIONS
                + REGEX_INTERVAL_CONJUNCTION
                + YEAR_OPTIONS
            + ")";

    public static final String YEAR_3_4_DIGITS_SPECIAL_PREFIX =
            "("
                + "anul\\s*\\d{1,2}="
            + ")";

    // "anul 13=1800/1801"; "110/109 a. chr."; "112 sau 111 î.chr."
    public static final String YEAR_3_4_DIGITS_SPECIAL_INTERVAL = CASE_INSENSITIVE + TEXT_START
            + "("
                + "\\d{3,4}" + AD_BC_OPTIONAL
                + YEAR_OR_SEPARATOR
                + "\\d{3,4}" + AD_BC_OPTIONAL
            + ")" + TEXT_END;

    // "(1)838"; "15(6)3"; "173[1]"; "184(5)"; "1700(?!)"; "(15…)"
    public static final String UNKNOWN_YEARS =
            "("
                + "(" + "[\\[\\(\\]\\)\\?\\!\\d\\…]{5,}" + ")"
            + ")";
}
