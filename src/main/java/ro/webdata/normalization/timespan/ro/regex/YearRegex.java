package ro.webdata.normalization.timespan.ro.regex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

public class YearRegex {
    private YearRegex() {}

    private static final String BRACKETS_START =
            "("
                + "?<=[\\[\\(]"
            + ")";
    private static final String BRACKETS_END =
            "("
                + "?=[\\]\\)]"
            + ")";

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

    public static final String YEAR_GROUP = "(\\({0,1}\\d{1,}(\\.\\d{1,})?\\){0,1}\\s*\\d{1,})";
    public static final String YEAR_NOTATION = YEAR_GROUP + AD_BC_OPTIONAL;

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
    public static final String YEAR_OPTIONS = CASE_INSENSITIVE +
            "("
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
