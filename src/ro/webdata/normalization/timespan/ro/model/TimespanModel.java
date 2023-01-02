package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class TimespanModel {
    private ArrayList<HashMap<String, String>> dbpediaEdgesUris = new ArrayList<>();
    private TreeSet<String> dbpediaUris = new TreeSet<>();
    private String residualValue = Const.EMPTY_VALUE_PLACEHOLDER;
    private ArrayList<String> types = new ArrayList<>();

    public TimespanModel(String value) {
        setResidualValue(value);
    }

    public TimespanModel(TreeSet<String> dbpediaUris, ArrayList<HashMap<String, String>> dbpediaEdgesUris, String value, ArrayList<String> types) {
        setDBpediaUris(dbpediaUris);
        addDBpediaEdgesUris(dbpediaEdgesUris);
        setResidualValue(value);
        setTypes(types);
    }

    public void addDBpediaEdgesUris(ArrayList<HashMap<String, String>> edgesUris) {
        this.dbpediaEdgesUris.addAll(edgesUris);
    }

    public ArrayList<HashMap<String, String>> getDBpediaEdgesUris() {
        return this.dbpediaEdgesUris;
    }

    public TreeSet<String> getDBpediaUris() {
        return this.dbpediaUris;
    }

    public String getResidualValue() {
        return this.residualValue;
    }

    public ArrayList<String> getTypes() {
        return this.types;
    }

    private void setDBpediaUris(TreeSet<String> timespanList) {
        this.dbpediaUris = timespanList;
    }

    private void setTypes(ArrayList<String> types) { this.types = types; }

    private void setResidualValue(String residualValue) {
        this.residualValue = residualValue;
    }
}
