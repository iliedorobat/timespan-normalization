package ro.webdata.normalization.timespan.ro;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import ro.webdata.normalization.timespan.ro.model.DBpediaModel;
import ro.webdata.normalization.timespan.ro.model.TimespanModel;

import java.util.*;

// FIXME:
//  nedatat (1852 ? – 1860 ?)
//  an 1  an 21  etc.
//  1/2 mil. 5 - sec. I al mil. 4 a.Chr.
//  2 a.chr - 14 p.chr
public class TimeExpression {
    private static final Gson GSON = new Gson();
    private static final String SEPARATOR = "|";
    transient private String preparedValue;

    @SerializedName("inputValue")
    private String inputValue;

    @SerializedName("timeSeries")
    private List<TimespanModel> timespanModels;

    public static String getHeaders() {
        List<String> headers = new ArrayList<>(){{
            add("input value");
            add("prepared value");
            add("normalized values");
            add("normalized edge values");
        }};

        return String.join(SEPARATOR, headers);
    }

    /**
     * Set the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)
     * @param inputValue The original value
     */
    public TimeExpression(String inputValue) {
        this(inputValue, false, false);
    }

    /**
     * Set the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)
     * @param inputValue The original value
     * @param historicalOnly Flag which specifies whether the Framework will only handle
     *                       historical dates (future dates will be ignored)
     * @param sanitize Flag specifying if the custom method TimeSanitizeUtils.sanitizeValue
     *                 will be used to sanitize values. Use "true" only if you use this
     *                 framework on LIDO datasets.
     */
    public TimeExpression(String inputValue, boolean historicalOnly, boolean sanitize) {
        try {
            String sanitizedValue = sanitize
                    ? TimeSanitizeUtils.sanitizeValue(inputValue)
                    : inputValue;

            this.inputValue = inputValue;
            this.preparedValue = TimeUtils.normalizeChristumNotation(sanitizedValue);
            this.timespanModels = TimespanUtils.prepareTimespanModels(inputValue, historicalOnly, sanitize);
        } catch (Exception e) {
            e.printStackTrace();
            this.timespanModels = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return inputValue
                + SEPARATOR + preparedValue
                + SEPARATOR + this.getDBpediaItems()
                + SEPARATOR + this.getDBpediaEdges();
    }

    public String serialize() {
        return GSON.toJson(this);
    }

    private List<Map<String, DBpediaModel>> getDBpediaEdges() {
        List<Map<String, DBpediaModel>> edges = new ArrayList<>();

        for (TimespanModel timespanModel : this.timespanModels) {
            edges.add(timespanModel.getDBpediaEdges());
        }

        return edges;
    }

    private Set<DBpediaModel> getDBpediaItems() {
        Set<DBpediaModel> items = new LinkedHashSet<>();

        for (TimespanModel timespanModel : this.timespanModels) {
            items.addAll(timespanModel.getDBpediaItems());
        }

        return items;
    }
}
