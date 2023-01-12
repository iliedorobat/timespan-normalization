package ro.webdata.normalization.timespan.ro.model.imprecise;

import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.model.TimespanModel;

public class DatelessModel extends TimePeriodModel {
    private String value;

    public DatelessModel(TimespanModel timespanModel, String original, String value) {
        this.value = value;
    }
}
