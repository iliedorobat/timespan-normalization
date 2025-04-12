package ro.webdata.normalization.timespan.ro;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import ro.webdata.normalization.timespan.ro.model.DBpediaModel;
import ro.webdata.normalization.timespan.ro.model.TimespanModel;

import java.util.*;

// FIXME:
//  1880-1890 (nedatat)
//  1884 martie 28/aprilie 09
//  1893-1902 (nedatat)
//  1903-1914 (nedatat)
//  1907 (nedatat)
//  1910 (nedatat)
//  1912-1914 (nedatat)
//  an 1  an 21  etc.
//  mileniile v-iva. chr.
//  octombrie 23, 1777
public class TimeExpression {
    private static final Gson GSON = new Gson();
    transient private String separator = "\n";
    transient private String sanitizedValue;

    @SerializedName("initial")
    private String value;

    @SerializedName("edges")
    private List<Map<String, DBpediaModel>> dbpediaEdges;

    @SerializedName("periods")
    private Set<DBpediaModel> dbpediaItems;

    // TODO:
    transient private List<TimespanModel> timespanModels;

    public static String getHeaders() {
        List<String> headers = new ArrayList<>(){{
            add("initial value");
            add("prepared value");
            add("normalized values");
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
    public TimeExpression(String value, boolean historicalOnly, String separator) {
        String sanitizedValue = TimeSanitizeUtils.sanitizeValue(value);
        this.timespanModels = TimespanUtils.prepareTimespanModels(sanitizedValue, historicalOnly);

        this.value = value;
        this.sanitizedValue = TimeUtils.normalizeChristumNotation(sanitizedValue);
        this.dbpediaEdges = this.getDBpediaEdges();
        this.dbpediaItems = this.getDBpediaItems();

        if (separator != null)
            this.separator = separator;
    }

    // TODO: remove
    private List<Map<String, DBpediaModel>> getDBpediaEdges() {
        List<Map<String, DBpediaModel>> edges = new ArrayList<>();

        for (TimespanModel timespanModel : this.timespanModels) {
            edges.add(timespanModel.getDBpediaEdges());
        }

        return edges;
    }

    // TODO: remove
    private Set<DBpediaModel> getDBpediaItems() {
        Set<DBpediaModel> items = new LinkedHashSet<>();

        for (TimespanModel timespanModel : this.timespanModels) {
            items.addAll(timespanModel.getDBpediaItems());
        }

        return items;
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
}
