package main.java.ro.webdata.normalization.timespan.ro;

import java.util.TreeSet;

public class TimeExpression {
    private String separator = "\n";
    private String value = null;
    private String sanitizedValue = null;
    private TreeSet<String> normalizedValues = new TreeSet<>();

    /**
     * Set the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)
     * @param value The original value
     * @param separator Value separator
     */
    public TimeExpression(String value, String separator) {
        this.value = value;
        this.sanitizedValue = TimeSanitizeUtils.sanitizeValue(value, null);
        this.normalizedValues = TimespanUtils.getTimespanSet(this.sanitizedValue);
        if (separator != null)
            this.separator = separator;
    }

    @Override
    public String toString() {
        return value
                + separator + sanitizedValue
                + separator + normalizedValues;
    }

    public String getValue() {
        return value;
    }

    public String getSanitizedValue() {
        return sanitizedValue;
    }

    public TreeSet<String> getNormalizedValues() {
        return normalizedValues;
    }
}
