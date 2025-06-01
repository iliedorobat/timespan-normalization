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

    public static final String YEAR = "(\\({0,1}\\d{1,}(\\.\\d{1,})?\\){0,1}\\s*\\d{1,})";

    public static final String YEAR_AD_BC = YEAR + AD_BC_OPTIONAL;

    public static final String YEAR_OPTIONS =
            "("
                + TEXT_START
                + YEAR_AD_BC
                + TEXT_END
            + ")";

    public static final String YEAR_INTERVAL_BASE = CASE_INSENSITIVE + TEXT_START
            + "("
                + YEAR_AD_BC
                + REGEX_INTERVAL_DELIMITER
                + YEAR_AD_BC
            + ")";

    public static final String YEAR_INTERVAL_PREFIXED =  CASE_INSENSITIVE + TEXT_START
            + "("
                + REGEX_INTERVAL_PREFIX
                + YEAR_AD_BC
                + REGEX_INTERVAL_CONJUNCTION
                + YEAR_AD_BC
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
