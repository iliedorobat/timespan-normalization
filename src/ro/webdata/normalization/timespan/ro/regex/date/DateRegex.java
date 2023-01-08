package ro.webdata.normalization.timespan.ro.regex.date;

import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

/**
 * Regular expressions for those time intervals that are stored
 * as a date (having a year, a month and a day)
 */
public class DateRegex {
    private DateRegex() {}

    // REGEX_DATE_INTERVAL_SEPARATOR needs to be "([ ]+-[ ]+)"
    public static final String REGEX_DATE_INTERVAL_SEPARATOR = TimespanRegex.REGEX_DATE_INTERVAL_SEPARATOR;
    private static final String REGEX_OR = TimespanRegex.REGEX_OR;
    private static final String TEXT_START = TimespanRegex.TEXT_START;
    private static final String TEXT_END = TimespanRegex.TEXT_END;

    // d{3,} allows avoiding the month-day pattern (E.g.: "noiembrie 22")
    private static final String DATE_DMY_DOT =
            "("
                + "\\d{1,2}[\\.]{1}\\d{2}[\\.]{1}\\d{3,}"
                + TimespanRegex.AD_BC_OPTIONAL
            + ")";        // E.g.: "01.01.1911"
    private static final String DATE_DMY_DOT_PARTIAL =
            "("
                + "\\d{1,2}[\\.]{1}\\d{2}"
                + "("
                    + "[\\.]{1}\\d{3,}"
                + ")*"
                + TimespanRegex.AD_BC_OPTIONAL
            + ")";

    private static final String DATE_DMY_SLASH =
            "("
                + "\\d{1,2}[/]{1}\\d{2}[/]{1}\\d{3,}"
                + TimespanRegex.AD_BC_OPTIONAL
            + ")";          // E.g.: "21/01/1916"
    private static final String DATE_DMY_SLASH_PARTIAL =
            "("
                + "\\d{1,2}[/]{1}\\d{2}"
                + "("
                    + "[/]{1}\\d{3,}"
                + ")*"
                + TimespanRegex.AD_BC_OPTIONAL
            + ")";

    private static final String DATE_DMY_SPACE =
            "("
            + "\\d{1,2}\\s*{1}\\d{2}\\s*{1}\\d{3,}"
            + TimespanRegex.AD_BC_OPTIONAL
            + ")";          // E.g.: "7 06 1911"
    private static final String DATE_DMY_SPACE_PARTIAL =
            "("
                + "\\d{1,2}\\s*{1}\\d{2}"
                + "("
                    + "\\s*{1}\\d{3,}"
                + ")*"
                + TimespanRegex.AD_BC_OPTIONAL
            + ")";

    private static final String DATE_DMY_TEXT =
            "("
                + "\\d{1,2}[, ]+"
                + TimespanRegex.MONTHS_RO
                + "[, ]+\\d{3,}"
                + TimespanRegex.AD_BC_OPTIONAL
            + ")";  // E.g.: "9 iulie 1807"
    public static final String DATE_DMY_TEXT_PARTIAL =
            "("
                + "\\d{1,2}[, ]+"
                + TimespanRegex.MONTHS_RO
                + "("
                    + "[, ]+\\d{3,}"
                    + TimespanRegex.AD_BC_OPTIONAL
                + ")*"
            + ")";  // E.g.: "10 iunie - 15 octombrie 1382"

    private static final String DATE_YMD_DASH =
            "("
                + "\\d{3,}[-]{1}\\d{2}[-]{1}\\d{1,2}"
                + TimespanRegex.AD_BC_OPTIONAL
            + ")";           // E.g.: "1698-10-15"

    private static final String DATE_YMD_TEXT =
            "("
                + "\\d{3,}[, ]+"
                + TimespanRegex.MONTHS_RO
                + "[, ]+\\d{1,2}"
                + TimespanRegex.AD_BC_OPTIONAL
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

    public static final String DATE_DMY_INTERVAL = TimespanRegex.CASE_INSENSITIVE
            + "("
                + DATE_DMY_INTERVAL_START
                + REGEX_DATE_INTERVAL_SEPARATOR
                + DATE_DMY_INTERVAL_END
            + ")";
    public static final String DATE_YMD_INTERVAL = TimespanRegex.CASE_INSENSITIVE
            + "("
                + DATE_YMD_INTERVAL_START
                + REGEX_DATE_INTERVAL_SEPARATOR
                + DATE_YMD_INTERVAL_END
            + ")";

    public static final String DATE_DMY_OPTIONS = TimespanRegex.CASE_INSENSITIVE
            + "("
                + "(" + TEXT_START + DATE_DMY_DOT + TEXT_END + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_SLASH + TEXT_END + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_SPACE+ ")" + REGEX_OR
                + "(" + TEXT_START + DATE_DMY_TEXT + TEXT_END + ")"
            + ")";
    public static final String DATE_YMD_OPTIONS = TimespanRegex.CASE_INSENSITIVE
            + "("
                + "(" + TEXT_START + DATE_YMD_DASH + TEXT_END + ")" + REGEX_OR
                + "(" + TEXT_START + DATE_YMD_TEXT + TEXT_END + ")"
            + ")";
}
