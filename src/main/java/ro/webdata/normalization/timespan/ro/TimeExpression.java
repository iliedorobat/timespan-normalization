package ro.webdata.normalization.timespan.ro;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;
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

        return String.join("|", headers);
    }

    /**
     * Set the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)
     * @param inputValue The original value
     * @param separator Value separator
     */
    public TimeExpression(String inputValue, boolean historicalOnly, String separator) {
        this.inputValue = inputValue;
        this.preparedValue = TimeUtils.normalizeChristumNotation(
                StringUtils.stripAccents(
                    TimeSanitizeUtils.sanitizeValue(inputValue)
            )
        );
        this.timespanModels = TimespanUtils.prepareTimespanModels(inputValue, historicalOnly);

        if (separator != null)
            this.separator = separator;
    }

    @Override
    public String toString() {
        return inputValue
                + separator + preparedValue
                + separator + this.getDBpediaItems()
                + separator + this.getDBpediaEdges();
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
