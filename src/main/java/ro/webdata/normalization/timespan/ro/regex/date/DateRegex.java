package ro.webdata.normalization.timespan.ro.regex.date;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

/**
 * Regular expressions for those time intervals that are stored
 * as a date (having a year, a month and a day)
 */
public class DateRegex {
    private DateRegex() {}

    // d{3,} allows avoiding the month-day pattern (E.g.: "noiembrie 22")
    private static final String DATE_DMY_DOT =
            "("
                + "\\d{1,2}[\\.]{1}\\d{2}[\\.]{1}\\d{3,}"
                + AD_BC_OPTIONAL
            + ")";        // E.g.: "01.01.1911"
    private static final String DATE_DMY_DOT_PARTIAL =
            "("
                + "\\d{1,2}[\\.]{1}\\d{2}"
                + "("
                    + "[\\.]{1}\\d{3,}"
                + ")*"
                + AD_BC_OPTIONAL
            + ")";

    private static final String DATE_DMY_SLASH =
            "("
                + "\\d{1,2}[/]{1}\\d{2}[/]{1}\\d{3,}"
                + AD_BC_OPTIONAL
            + ")";          // E.g.: "21/01/1916"
    private static final String DATE_DMY_SLASH_PARTIAL =
            "("
                + "\\d{1,2}[/]{1}\\d{2}"
                + "("
                    + "[/]{1}\\d{3,}"
                + ")*"
                + AD_BC_OPTIONAL
            + ")";

    private static final String DATE_DMY_SPACE =
            "("
            + "\\d{1,2}\\s*{1}\\d{2}\\s*{1}\\d{3,}"
            + AD_BC_OPTIONAL
            + ")";          // E.g.: "7 06 1911"
    private static final String DATE_DMY_SPACE_PARTIAL =
            "("
                + "\\d{1,2}\\s*{1}\\d{2}"
                + "("
                    + "\\s*{1}\\d{3,}"
                + ")*"
                + AD_BC_OPTIONAL
            + ")";

    private static final String DATE_DMY_TEXT =
            "("
                + "\\d{1,2}[,\\s]+"
                + MONTHS_RO
                + "[,\\s]+\\d{3,}"
                + AD_BC_OPTIONAL
            + ")";  // E.g.: "9 iulie 1807"
    public static final String DATE_DMY_TEXT_PARTIAL =
            "("
                + "\\d{1,2}[, ]+"
                + MONTHS_RO
                + "("
                    + "[,\\s]+\\d{3,}"
                    + AD_BC_OPTIONAL
                + ")*"
            + ")";  // E.g.: "10 iunie - 15 octombrie 1382"

    private static final String DATE_YMD_DASH =
            "("
                + "\\d{3,}[-]{1}\\d{2}[-]{1}\\d{1,2}"
                + AD_BC_OPTIONAL
            + ")";  // E.g.: "1698-10-15"

    private static final String DATE_YMD_TEXT =
            "("
                + "\\d{3,}[,\\s]+"
                + MONTHS_RO
                + "[,\\s]+\\d{1,2}"
                + AD_BC_OPTIONAL
            + ")";  // E.g.: "1752 aprilie 25"

    private static final String DATE_DMY_INTERVAL_START =
            "("
                + "(" + TEXT_START + DATE_DMY_DOT_PARTIAL + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_SLASH_PARTIAL + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_SPACE_PARTIAL + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_TEXT_PARTIAL + ")"
            + ")";
    private static final String DATE_YMD_INTERVAL_START =
            "("
                + "(" + TEXT_START + DATE_YMD_DASH + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_YMD_TEXT + ")"
            + ")";
    private static final String DATE_DMY_INTERVAL_END =
            "("
                + "(" + DATE_DMY_DOT + TEXT_END + ")" + REGEX_OR
                + "(" + DATE_DMY_SLASH + TEXT_END + ")" + REGEX_OR
                + "(" + DATE_DMY_SPACE + TEXT_END + ")" + REGEX_OR
                + "(" + DATE_DMY_TEXT + TEXT_END + ")"
            + ")";
    private static final String DATE_YMD_INTERVAL_END =
            "("
                + "(" + DATE_YMD_DASH + TEXT_END + ")" + REGEX_OR
                + "(" + DATE_YMD_TEXT + TEXT_END + ")"
            + ")";

    public static final String DATE_DMY_INTERVAL = CASE_INSENSITIVE
            + "("
                + DATE_DMY_INTERVAL_START
                + REGEX_INTERVAL_DELIMITER
                + DATE_DMY_INTERVAL_END
            + ")";
    public static final String DATE_YMD_INTERVAL = CASE_INSENSITIVE
            + "("
                + DATE_YMD_INTERVAL_START
                + REGEX_INTERVAL_DELIMITER
                + DATE_YMD_INTERVAL_END
            + ")";

    public static final String DATE_DMY_OPTIONS = CASE_INSENSITIVE
            + "("
                + "(" + TEXT_START + DATE_DMY_DOT + TEXT_END + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_SLASH + TEXT_END + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_SPACE+ ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_TEXT + TEXT_END + ")"
            + ")";
    public static final String DATE_YMD_OPTIONS = CASE_INSENSITIVE
            + "("
                + "(" + TEXT_START + DATE_YMD_DASH + TEXT_END + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_YMD_TEXT + TEXT_END + ")"
            + ")";
}
