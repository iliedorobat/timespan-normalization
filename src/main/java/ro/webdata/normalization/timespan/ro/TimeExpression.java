package ro.webdata.normalization.timespan.ro;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import ro.webdata.normalization.timespan.ro.model.DBpediaModel;
import ro.webdata.normalization.timespan.ro.model.TimespanModel;

import java.util.*;

public class TimeExpression {
    private static final Gson GSON = new Gson();
    transient private String separator = "\n";

    @SerializedName("initial")
    private String value;

    @SerializedName("prepared")
    private String sanitizedValue;

    @SerializedName("normalizedEdges")
    private List<Map<String, DBpediaModel>> dbpediaEdges;

    @SerializedName("normalizedValues")
    private Set<DBpediaModel> dbpediaItems;

    public static String getHeaders() {
        List<String> headers = new ArrayList<>(){{
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
        String sanitizedValue = TimeSanitizeUtils.sanitizeValue(value, null);
        TimespanModel timespanModel = TimespanUtils.prepareTimespanModel(sanitizedValue);

        this.value = value;
        this.sanitizedValue = TimeUtils.normalizeChristumNotation(sanitizedValue);
        this.dbpediaEdges = timespanModel.getDBpediaEdges();
        this.dbpediaItems = timespanModel.getDBpediaItems();

        if (separator != null)
            this.separator = separator;
    }

    @Override
    public String toString() {
        return value
                + separator + sanitizedValue
                + separator + dbpediaItems
                + separator + dbpediaEdges;
    }

    public String serialize() {
        if (dbpediaItems.isEmpty()) {
            return "{}";
        }

        return GSON.toJson(this);
    }

    public String getValue() {
        return value;
    }

    public String getSanitizedValue() {
        return sanitizedValue;
    }

    public Set<DBpediaModel> getDbpediaItems() {
        return dbpediaItems;
    }

    public List<Map<String, DBpediaModel>> getDbpediaEdges() {
        return this.dbpediaEdges;
    }
}
