package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Const;

import java.util.*;
import java.util.stream.Collectors;

public class TimespanModel {
    private List<Map<String, DBpediaModel>> dbpediaEdges = new ArrayList<>();
    private Set<DBpediaModel> dbpediaItems = new LinkedHashSet<>();
    private String residualValue = Const.EMPTY_VALUE_PLACEHOLDER;
    private List<String> types = new ArrayList<>();

    public TimespanModel(String value) {
        setResidualValue(value);
    }

    public TimespanModel(Set<DBpediaModel> dbpediaItems, List<Map<String, DBpediaModel>> dbpediaEdges, String value, List<String> types) {
        setDBpediaItems(dbpediaItems);
        addDBpediaEdges(dbpediaEdges);
        setResidualValue(value);
        setTypes(types);
    }

    public void addDbpediaEdges(Map<String, DBpediaModel> edges) {
        this.dbpediaEdges.add(edges);
    }

    public void addDBpediaEdges(List<Map<String, DBpediaModel>> edgesList) {
        this.dbpediaEdges.addAll(edgesList);
    }

    public void addDBpediaItems(String[] matchedList) {
        List<DBpediaModel> list = Arrays.stream(matchedList)
                .map(DBpediaModel::new)
                .collect(Collectors.toCollection(ArrayList::new));
        this.dbpediaItems.addAll(list);
    }

    public void addType(String type) {
        this.types.add(type);
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

    public List<String> getTypes() {
        return this.types;
    }

    private void setDBpediaItems(Set<DBpediaModel> timespanList) {
        this.dbpediaItems = timespanList;
    }

    private void setTypes(List<String> types) { this.types = types; }

    public void setResidualValue(String residualValue) {
        this.residualValue = residualValue;
    }
}
