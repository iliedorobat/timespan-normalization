package main.java.ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Const;

import java.util.TreeSet;

public class TimespanModel {
    private TreeSet<String> timespanList = new TreeSet<>();
    private String residualValue = Const.EMPTY_VALUE_PLACEHOLDER;

    public TimespanModel(String value) {
        setResidualValue(value);
    }

    public TimespanModel(TreeSet<String> treeSet, String value) {
        setTimespanSet(treeSet);
        setResidualValue(value);
    }

    //FIXME: make it private
    public void clearTimespanSet() {
        this.timespanList.clear();
    }

    public TreeSet<String> getTimespanSet() {
        return timespanList;
    }

    public String getResidualValue() {
        return residualValue;
    }

    private void setTimespanSet(TreeSet<String> timespanList) {
        this.timespanList = timespanList;
    }

    private void setResidualValue(String residualValue) {
        this.residualValue = residualValue;
    }
}
