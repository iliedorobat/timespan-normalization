package ro.webdata.normalization.timespan.ro;

import ro.webdata.normalization.timespan.ro.model.TimespanModel;

import java.util.*;

public class TimeExpression {
    private String separator = "\n";
    private String value = null;
    private String sanitizedValue = null;
    private TreeSet<String> edgesValues = new TreeSet<>();
    private TreeSet<String> normalizedValues = new TreeSet<>();
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
        this.edgesValues = prepareEdgesValues(timespanModel);
        this.types = timespanModel.getTypes();

        if (separator != null)
            this.separator = separator;
    }

    private TreeSet<String> prepareEdgesValues(TimespanModel timespanModel) {
        HashSet<String> edgesValues = new HashSet<>();
        ArrayList<HashMap<String, String>> edgesEntries = timespanModel.getDBpediaEdgesUris();

        for (HashMap<String, String> item : edgesEntries) {
            for (Map.Entry<String, String> entry : item.entrySet()) {
                String edge = entry.getValue();

                if (edge != null)
                    edgesValues.add(edge);
            }
        }

        ArrayList<String> sorted = new ArrayList<>(edgesValues);
        Collections.sort(sorted);

        return new TreeSet<>(sorted);
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

    public TreeSet<String> getNormalizedValues() {
        return normalizedValues;
    }
}
