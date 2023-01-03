package ro.webdata.normalization.timespan.ro.model.timePeriod;

import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

public class MillenniumModel extends TimePeriodModel {
    public MillenniumModel(String value) {
        setMillenniumModel(value);
    }

    public void setMillenniumModel(String value) {
        String preparedValue = TimePeriodUtils.sanitizeTimePeriod(value);
        String[] intervalValues = preparedValue.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(intervalValues[0], intervalValues[1]);

            Integer endValue = TimePeriodUtils.getEndTime(intervalValues, this.eraStart);
            Integer startValue = TimePeriodUtils.getStartTime(intervalValues, this.eraStart);

            setMillenniumDate(endValue, TimeUtils.END_PLACEHOLDER);
            setMillenniumDate(startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(value, value);

            Integer millenniumValue = TimePeriodUtils.timePeriodToNumber(preparedValue);

            setMillenniumDate(millenniumValue, TimeUtils.END_PLACEHOLDER);
            setMillenniumDate(millenniumValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setMillenniumDate(Integer millennium, String position) {
        if (millennium != null)
            setMillennium(millennium, position);
    }
}
