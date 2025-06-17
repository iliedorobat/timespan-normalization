package ro.webdata.normalization.timespan.ro.regex.date;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

/**
 * Regular expressions for those time intervals that are stored
 * as a date (having a year, a month and a day)
 */
public class DateRegex {
    private DateRegex() {}

    // d{3,} allows avoiding the month-day pattern (E.g.: "noiembrie 22")
    private static final String DATE_DMY_DOT = "\\d{1,2}\\.\\d{1,2}\\.\\d{3,}"; // E.g.: "01.01.1911"
    private static final String DATE_DMY_SLASH = "\\d{1,2}\\/\\d{2}\\/\\d{3,}"; // E.g.: "21/01/1916"
    private static final String DATE_DMY_SPACE = "\\d{1,2}\\s+\\d{2}\\s+\\d{3,}"; // E.g.: "7 06 1911"
    private static final String DATE_DMY_TEXT = "\\d{1,2}[,\\s]+" + MONTHS + "[,\\s]+\\d{3,}";  // E.g.: "9 iulie 1807"
    private static final String DATE_DMY =
            "("
                + DATE_DMY_DOT + REGEX_OR
                + DATE_DMY_SLASH + REGEX_OR
                + DATE_DMY_SPACE + REGEX_OR
                + DATE_DMY_TEXT
            + ")" + AD_BC_OPTIONAL;

    private static final String DATE_DMY_DOT_PARTIAL = "\\d{1,2}\\.\\d{2}" + "(\\.\\d{3,})?";
    private static final String DATE_DMY_SLASH_PARTIAL = "\\d{1,2}\\/\\d{2}" + "(\\/\\d{3,})?";
    private static final String DATE_DMY_SPACE_PARTIAL = "\\d{1,2}\\s+\\d{2}" + "(\\s+\\d{3,})?";
    private static final String DATE_DMY_TEXT_PARTIAL = "\\d{1,2}[, ]+" + MONTHS + "([,\\s]+\\d{3,})?"; // E.g.: "10 iunie - 15 octombrie 1382"
    private static final String DATE_DMY_PARTIAL =
            "("
                + DATE_DMY_DOT_PARTIAL + REGEX_OR
                + DATE_DMY_SLASH_PARTIAL + REGEX_OR
                + DATE_DMY_SPACE_PARTIAL + REGEX_OR
                + DATE_DMY_TEXT_PARTIAL + REGEX_OR
                + "\\d{1,2}\\s*" + MONTHS + "?"
            + ")" + AD_BC_OPTIONAL;

    private static final String DATE_YMD_DASH = "\\d{3,}[-]{1}\\d{2}[-]{1}\\d{1,2}"; // E.g.: "1698-10-15"
    private static final String DATE_YMD_TEXT = "\\d{3,}[,\\s]+" + MONTHS + "[,\\s]+\\d{1,2}"; // E.g.: "1752 aprilie 25"
    private static final String DATE_YMD =
            "("
                + DATE_YMD_DASH + REGEX_OR
                + DATE_YMD_TEXT
            + ")" + AD_BC_OPTIONAL;

    private static final String DATE_DMY_INTERVAL_START = TEXT_START + DATE_DMY_PARTIAL;
    private static final String DATE_YMD_INTERVAL_START = TEXT_START + DATE_YMD;
    private static final String DATE_DMY_INTERVAL_END = DATE_DMY + TEXT_END;
    private static final String DATE_YMD_INTERVAL_END = DATE_YMD + TEXT_END;

    public static final String DATE_DMY_INTERVAL = CASE_INSENSITIVE
            + DATE_DMY_INTERVAL_START
            + REGEX_INTERVAL_DELIMITER
            + DATE_DMY_INTERVAL_END;
    public static final String DATE_YMD_INTERVAL = CASE_INSENSITIVE
            + DATE_YMD_INTERVAL_START
            + REGEX_INTERVAL_DELIMITER
            + DATE_YMD_INTERVAL_END;

    public static final String DATE_DMY_OPTIONS = CASE_INSENSITIVE + TEXT_START + DATE_DMY + TEXT_END;
    public static final String DATE_YMD_OPTIONS = CASE_INSENSITIVE + TEXT_START + DATE_YMD + TEXT_END;
}
