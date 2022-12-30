package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Const;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class TimespanModel {
    private TreeSet<String> timespanList = new TreeSet<>();
    private String residualValue = Const.EMPTY_VALUE_PLACEHOLDER;
    private ArrayList<String> types = new ArrayList<>();

    public TimespanModel(String value) {
        setResidualValue(value);
    }

    public TimespanModel(TreeSet<String> treeSet, String value, ArrayList<String> types) {
        setTimespanSet(treeSet);
        setResidualValue(value);
        setTypes(types);
    }

    public TreeSet<String> getTimespanSet() {
        return timespanList;
    }

    public String getResidualValue() {
        return residualValue;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    private void setTimespanSet(TreeSet<String> timespanList) {
        this.timespanList = timespanList;
    }

    private void setTypes(ArrayList<String> types) {
        Set<String> set = new TreeSet<>(types);
        this.types = new ArrayList<>(set);
    }

    private void setResidualValue(String residualValue) {
        this.residualValue = residualValue;
    }
}
