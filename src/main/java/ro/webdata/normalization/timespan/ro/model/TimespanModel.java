package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Const;

import java.util.*;
import java.util.stream.Collectors;

public class TimespanModel {
    private Map<String, DBpediaModel> dbpediaEdges = new HashMap<>();
    private Set<DBpediaModel> dbpediaItems = new LinkedHashSet<>();
    private String residualValue = Const.EMPTY_VALUE_PLACEHOLDER;

    public TimespanModel(TimePeriodModel timePeriod, String[] matchedList, String matchedValue, String matchedType, String residualValue) {
        setDbpediaEdges(timePeriod, matchedType, matchedValue);
        setDBpediaItems(matchedList, matchedType, matchedValue);
        setResidualValue(residualValue);
    }

    public Map<String, DBpediaModel> getDBpediaEdges() {
        return this.dbpediaEdges;
    }

    public Set<DBpediaModel> getDBpediaItems() {
        return this.dbpediaItems;
    }

    public String getResidualValue() {
        return this.residualValue;
    }

    public void setDbpediaEdges(TimePeriodModel timePeriod, String matchedType, String matchedValue) {
        Map<String, DBpediaModel> edges = new HashMap<>();
        String startUri = timePeriod.toDBpediaStartUri(matchedType);
        String endUri = timePeriod.toDBpediaEndUri(matchedType);

        // E.g.: "începutul mil.al XX-lea"
        if (startUri == null || endUri == null) {
            return;
        }

        DBpediaModel start = new DBpediaModel(startUri, matchedType, matchedValue);
        DBpediaModel end = new DBpediaModel(endUri, matchedType, matchedValue);

        edges.put("start", start);
        edges.put("end", end);

        this.dbpediaEdges = edges;
    }

    public void setDBpediaItems(String[] matchedList, String matchedType, String matchedValue) {
        List<DBpediaModel> list = Arrays.stream(matchedList)
                .map(uri -> new DBpediaModel(uri, matchedType, matchedValue))
                .collect(Collectors.toCollection(ArrayList::new));
        this.dbpediaItems.addAll(list);
    }

    public void setResidualValue(String residualValue) {
        this.residualValue = residualValue;
    }
}
