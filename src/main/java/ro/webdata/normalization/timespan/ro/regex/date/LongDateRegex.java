package main.java.ro.webdata.normalization.timespan.ro.regex.date;

import main.java.ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

public class LongDateRegex {
    private LongDateRegex() {}

    public static final String DATE_SEPARATOR = ";";
    public static final String LONG_DATE_OPTIONS = TimespanRegex.CASE_INSENSITIVE
            + "("
                + "^"
                    + "s:[\\d]{1,2}"
                    + DATE_SEPARATOR + "a:[\\d]{1,4}"
                    + DATE_SEPARATOR +"l:[\\d]{1,2}"
                    + DATE_SEPARATOR +"z:[\\d]{1,2}"
                + "$"
            + ")";
}
