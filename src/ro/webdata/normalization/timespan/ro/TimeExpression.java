package ro.webdata.normalization.timespan.ro;

import ro.webdata.normalization.timespan.ro.model.TimespanModel;

import java.util.ArrayList;
import java.util.TreeSet;

public class TimeExpression {
    private String separator = "\n";
    private String value = null;
    private String sanitizedValue = null;
    private TreeSet<String> normalizedValues = new TreeSet<>();
    private ArrayList<String> types = new ArrayList<>();

    /**
     * Set the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)
     * @param value The original value
     * @param separator Value separator
     */
    public TimeExpression(String value, String separator) {
        this.value = value;
        this.sanitizedValue = TimeSanitizeUtils.sanitizeValue(value, null);
        TimespanModel timespanModel = TimespanUtils.prepareTimespanModel(this.sanitizedValue);
        this.normalizedValues = timespanModel.getTimespanSet();
        this.types = timespanModel.getTypes();

        if (separator != null)
            this.separator = separator;
    }

    @Override
    public String toString() {
        return value
                + separator + sanitizedValue
                + separator + normalizedValues
                + separator + types;
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
