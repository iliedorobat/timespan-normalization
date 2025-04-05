package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Const;

import java.util.*;

public class TimespanModel {
    private List<Map<String, String>> dbpediaEdgesUris = new ArrayList<>();
    private Set<String> dbpediaUris = new LinkedHashSet<>();
    private String residualValue = Const.EMPTY_VALUE_PLACEHOLDER;
    private List<String> types = new ArrayList<>();

    public TimespanModel(String value) {
        setResidualValue(value);
    }

    public TimespanModel(Set<String> dbpediaUris, List<Map<String, String>> dbpediaEdgesUris, String value, List<String> types) {
        setDBpediaUris(dbpediaUris);
        addDBpediaEdgesUris(dbpediaEdgesUris);
        setResidualValue(value);
        setTypes(types);
    }

    public void addDbpediaEdgesUri(Map<String, String> edgesUri) {
        this.dbpediaEdgesUris.add(edgesUri);
    }

    public void addDBpediaEdgesUris(List<Map<String, String>> edgesUris) {
        this.dbpediaEdgesUris.addAll(edgesUris);
    }

    public void addDBpediaUris(String[] matchedList) {
        this.dbpediaUris.addAll(Arrays.asList(matchedList));
    }

    public void addType(String type) {
        this.types.add(type);
    }

    public List<Map<String, String>> getDBpediaEdgesUris() {
        return this.dbpediaEdgesUris;
    }

    public Set<String> getDBpediaUris() {
        return this.dbpediaUris;
    }

    public String getResidualValue() {
        return this.residualValue;
    }

    public List<String> getTypes() {
        return this.types;
    }

    private void setDBpediaUris(Set<String> timespanList) {
        this.dbpediaUris = timespanList;
    }

    private void setTypes(List<String> types) { this.types = types; }

    public void setResidualValue(String residualValue) {
        this.residualValue = residualValue;
    }
}
