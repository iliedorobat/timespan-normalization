package main.java.ro.webdata.normalization.timespan.ro;

import java.util.TreeSet;

public class TimeExpression {
    private String separator = "\n";
    private String value = null;
    private String sanitizedValue = null;
    private TreeSet<String> dbpediaValues = new TreeSet<>();

    /**
     * Set the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)
     * @param value The original value
     * @param separator Value separator
     */
    public TimeExpression(String value, String separator) {
        this.value = value;
        this.sanitizedValue = TimeSanitizeUtils.sanitizeValue(value, null);
        this.dbpediaValues = TimespanUtils.getTimespanSet(value);
        if (separator != null)
            this.separator = separator;
    }

    @Override
    public String toString() {
        return value
                + separator + sanitizedValue
                + separator + dbpediaValues;
    }

    public String getValue() {
        return value;
    }

    public String getSanitizedValue() {
        return sanitizedValue;
    }

    public TreeSet<String> getDbpediaValues() {
        return dbpediaValues;
    }
}
