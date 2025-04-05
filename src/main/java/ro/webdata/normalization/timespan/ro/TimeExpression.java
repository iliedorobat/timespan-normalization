package ro.webdata.normalization.timespan.ro;

import ro.webdata.normalization.timespan.ro.model.TimespanModel;

import java.util.*;

public class TimeExpression {
    private String separator = "\n";
    private String value = null;
    private String sanitizedValue = null;
    private ArrayList<HashMap<String, String>> edgesValues = new ArrayList<>();
    private LinkedHashSet<String> normalizedValues = new LinkedHashSet<>();
    private ArrayList<String> types = new ArrayList<>();

    public static String getHeaders() {
        ArrayList<String> headers = new ArrayList<>(){{
            add("initial value");
            add("sanitized value");
            add("normalized values");
            add("timespan types");
            add("sanitized edge values");
        }};

        return String.join("|", headers);
    }

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
        this.normalizedValues = timespanModel.getDBpediaUris();
        this.edgesValues = timespanModel.getDBpediaEdgesUris();
        this.types = timespanModel.getTypes();

        if (separator != null)
            this.separator = separator;
    }

    @Override
    public String toString() {
        return value
                + separator + sanitizedValue
                + separator + normalizedValues
                + separator + types
                + separator + edgesValues;
    }

    public String getValue() {
        return value;
    }

    public String getSanitizedValue() {
        return sanitizedValue;
    }

    public LinkedHashSet<String> getNormalizedValues() {
        return normalizedValues;
    }

    public ArrayList<HashMap<String, String>> getEdgesValues() {
        return this.edgesValues;
    }
}
