package ro.webdata.normalization.timespan.ro.regex.date;

import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

/**
 * Regular expressions for those time intervals that are stored
 * as a date (having just a year and a month)
 */
public class ShortDateRegex {
    private ShortDateRegex() {}

    // d{3,} allows us to avoid the month-day pattern (E.g.: "noiembrie 22")
    private static final String SHORT_DATE_MY_TEXT = "("
                + TimespanRegex.MONTHS_RO
                + "[ ]+\\d{3,}"
                + TimespanRegex.AD_BC_OPTIONAL
            + ")";

    private static final String DATE_SHORT_MY_START_OPTIONS = "("
                + TEXT_START + SHORT_DATE_MY_TEXT
            + ")";
    private static final String DATE_MY_END_OPTIONS = "("
                + SHORT_DATE_MY_TEXT + TEXT_END
            + ")";

    public static final String DATE_MY_OPTIONS = TimespanRegex.CASE_INSENSITIVE
            + "("
                + TEXT_START + SHORT_DATE_MY_TEXT + TEXT_END
            + ")";
    public static final String DATE_MY_INTERVAL = TimespanRegex.CASE_INSENSITIVE
            + "("
                + "("
                    + DATE_SHORT_MY_START_OPTIONS
                    + REGEX_OR + TimespanRegex.MONTHS_RO
                + ")"
                + REGEX_INTERVAL_DELIMITER
                + DATE_MY_END_OPTIONS
            + ")";
}
