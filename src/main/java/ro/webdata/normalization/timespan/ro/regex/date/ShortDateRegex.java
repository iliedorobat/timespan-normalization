package ro.webdata.normalization.timespan.ro.regex.date;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

/**
 * Regular expressions for those time intervals that are stored
 * as a date (having just a year and a month)
 */
public class ShortDateRegex {
    private ShortDateRegex() {}

    // d{3,} allows us to avoid the month-day pattern (E.g.: "noiembrie 22")
    private static final String SHORT_DATE_MY_TEXT = "(" + MONTHS + "\\s+\\d{3,})";
    private static final String YEAR_INTERVAL = "\\s*[\\(\\[]?\\d+[\\)\\]]?\\s*";

    public static final String DATE_MY_INTERVAL = CASE_INSENSITIVE
            // Avoid match intervals consisting only of years (E.g.: "1789 - 1797")
            + "^(?!" + YEAR_INTERVAL + REGEX_INTERVAL_DELIMITER + YEAR_INTERVAL + "$).*"
            + TEXT_START
            // E.g.: noiembrie 1784 - aprilie 1785
            + "("
                + "(" + SHORT_DATE_MY_TEXT + AD_BC_OPTIONAL + ")"
                + REGEX_INTERVAL_DELIMITER
                + "(" + SHORT_DATE_MY_TEXT + AD_BC_OPTIONAL + ")"
            + ")"
            + REGEX_OR
            // E.g.: noiembrie 1784 - 1785
            + "("
                + SHORT_DATE_MY_TEXT + AD_BC_OPTIONAL
                + REGEX_INTERVAL_DELIMITER
                + "(" + SHORT_DATE_MY_TEXT + REGEX_OR + "\\d{3,}" + ")" + AD_BC_OPTIONAL
            + ")"
            + REGEX_OR
            // E.g.: 1784 - aprilie 1785
            + "("
                + "(" + SHORT_DATE_MY_TEXT + REGEX_OR + "\\d{3,}" + ")" + AD_BC_OPTIONAL
                + REGEX_INTERVAL_DELIMITER
                + SHORT_DATE_MY_TEXT + AD_BC_OPTIONAL
            + ")"
            + TEXT_END;

    public static final String DATE_MY_OPTIONS = CASE_INSENSITIVE
            + "("
                + TEXT_START
                + "(" + MONTHS + "\\s+\\d{3,}" + AD_BC_OPTIONAL + ")"
                + TEXT_END
            + ")";
}
