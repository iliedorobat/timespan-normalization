package ro.webdata.normalization.timespan.ro.model.imprecise;

import ro.webdata.echo.commons.Collection;

import java.util.TreeSet;

public class DatelessModel {
    private String value;

    public DatelessModel(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        // The ThreeSet is empty because the entry doesn't have any date
        TreeSet<String> centurySet = new TreeSet<>();
        return Collection.treeSetToDbpediaString(centurySet);
    }
}
