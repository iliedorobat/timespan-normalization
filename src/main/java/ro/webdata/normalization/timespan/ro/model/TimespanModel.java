package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Const;

import java.util.*;
import java.util.stream.Collectors;

public class TimespanModel {
    private List<Map<String, DBpediaModel>> dbpediaEdges = new ArrayList<>();
    private Set<DBpediaModel> dbpediaItems = new LinkedHashSet<>();
    private String residualValue = Const.EMPTY_VALUE_PLACEHOLDER;

    public TimespanModel(String value) {
        setResidualValue(value);
    }

    public void addDbpediaEdges(Map<String, DBpediaModel> edges) {
        this.dbpediaEdges.add(edges);
    }

    public void addDBpediaItems(String[] matchedList, String temporalType, String matchedValue) {
        List<DBpediaModel> list = Arrays.stream(matchedList)
                .map(uri -> new DBpediaModel(uri, temporalType, matchedValue))
                .collect(Collectors.toCollection(ArrayList::new));
        this.dbpediaItems.addAll(list);
    }

    public List<Map<String, DBpediaModel>> getDBpediaEdges() {
        return this.dbpediaEdges;
    }

    public Set<DBpediaModel> getDBpediaItems() {
        return this.dbpediaItems;
    }

    public String getResidualValue() {
        return this.residualValue;
    }

    private void setDBpediaItems(Set<DBpediaModel> timespanList) {
        this.dbpediaItems = timespanList;
    }

    public void setResidualValue(String residualValue) {
        this.residualValue = residualValue;
    }
}
