package ro.webdata.normalization.timespan.ro.model.timePeriod;

import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.model.TimespanModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

public class MillenniumModel extends TimePeriodModel {
    public MillenniumModel(TimespanModel timespanModel, String original, String value) {
        setMillenniumModel(timespanModel, original, value);
    }

    public void setMillenniumModel(TimespanModel timespanModel, String original, String value) {
        String preparedValue = TimePeriodUtils.sanitizeTimePeriod(value);
        String[] intervalValues = preparedValue.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1], false);

            Integer endValue = TimePeriodUtils.getEndTime(intervalValues, this.eraStart, false);
            Integer startValue = TimePeriodUtils.getStartTime(intervalValues, this.eraStart, false);

            setMillenniumDate(timespanModel, original, endValue, TimeUtils.END_PLACEHOLDER);
            setMillenniumDate(timespanModel, original, startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(original, value, value, false);

            Integer millenniumValue = TimePeriodUtils.timePeriodToNumber(preparedValue, false);

            setMillenniumDate(timespanModel, original, millenniumValue, TimeUtils.END_PLACEHOLDER);
            setMillenniumDate(timespanModel, original, millenniumValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setMillenniumDate(TimespanModel timespanModel, String original, Integer millennium, String position) {
        if (millennium != null) {
            setMillennium(timespanModel, original, millennium, position);
        }
    }
}
