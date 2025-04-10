package ro.webdata.normalization.timespan.ro.model.imprecise;

import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;

public class DatelessModel extends TimePeriodModel {
    private String value;

    public DatelessModel(String original, String value, boolean historicalOnly) {
        this.value = value;
    }
}
