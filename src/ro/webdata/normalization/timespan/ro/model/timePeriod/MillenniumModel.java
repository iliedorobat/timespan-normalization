package ro.webdata.normalization.timespan.ro.model.timePeriod;

import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

public class MillenniumModel extends TimePeriodModel {
    public MillenniumModel(String original, String value) {
        setMillenniumModel(original, value);
    }

    public void setMillenniumModel(String original, String value) {
        String preparedValue = TimePeriodUtils.sanitizeTimePeriod(value);
        String[] intervalValues = preparedValue.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1]);

            Integer endValue = TimePeriodUtils.getEndTime(intervalValues, this.eraStart);
            Integer startValue = TimePeriodUtils.getStartTime(intervalValues, this.eraStart);

            setMillenniumDate(original, endValue, TimeUtils.END_PLACEHOLDER);
            setMillenniumDate(original, startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(original, value, value);

            Integer millenniumValue = TimePeriodUtils.timePeriodToNumber(preparedValue);

            setMillenniumDate(original, millenniumValue, TimeUtils.END_PLACEHOLDER);
            setMillenniumDate(original, millenniumValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setMillenniumDate(String original, Integer millennium, String position) {
        if (millennium != null)
            setMillennium(original, millennium, position);
    }
}
