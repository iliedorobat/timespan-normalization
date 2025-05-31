package ro.webdata.normalization.timespan.ro.regex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.REGEX_INTERVAL_DELIMITER_EXTRA;
import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.REGEX_OR;

public class YearRegex {
    private YearRegex() {}

    private static final String TEXT_START = TimespanRegex.TEXT_START;
    private static final String TEXT_END = TimespanRegex.TEXT_END;
    private static final String CASE_INSENSITIVE = TimespanRegex.CASE_INSENSITIVE;
    private static final String REGEX_INTERVAL_PREFIX = TimespanRegex.REGEX_INTERVAL_PREFIX;
    private static final String BRACKETS_START =
            "("
                + "?<=[\\[\\(]"
            + ")";
    private static final String BRACKETS_END =
            "("
                + "?=[\\]\\)]"
            + ")";

    private static final String REGEX_INTERVAL_DELIMITER = TimespanRegex.REGEX_INTERVAL_DELIMITER;
    private static final String AD_BC_OPTIONAL = TimespanRegex.AD_BC_OPTIONAL;

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

    public static final String YEAR = "(\\({0,1}\\d{1,}(\\.\\d{1,})?\\){0,1}\\s*\\d{1,})";

    public static final String YEAR_AD_BC = YEAR + AD_BC_OPTIONAL;

    public static final String YEAR_OPTIONS =
            "("
                + TEXT_START
                + YEAR_AD_BC
                + TEXT_END
            + ")";

    public static final String YEAR_INTERVAL_EXTRA =  CASE_INSENSITIVE + TEXT_START
            + "("
                + REGEX_INTERVAL_PREFIX + "\\s*"
                + YEAR_AD_BC
                + REGEX_INTERVAL_DELIMITER_EXTRA
                + YEAR_AD_BC
            + ")";

    public static final String YEAR_INTERVAL_BASE = TEXT_START
            + "("
                + YEAR_AD_BC
                + REGEX_INTERVAL_DELIMITER
                + YEAR_AD_BC
            + ")";

    public static final String YEAR_3_4_DIGITS_SPECIAL_PREFIX =
            "("
                + "anul[ ]*\\d{1,2}="
            + ")";

    // "anul 13=1800/1801"; "110/109 a. chr."; "112 sau 111 î.chr."
    public static final String YEAR_3_4_DIGITS_SPECIAL_INTERVAL = TEXT_START
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
