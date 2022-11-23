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
            setIsInterval(true);

            // Set the end time before the start time in order to use it
            // as start time too, if this is missing, but the end year exists
            String endValue = TimeUtils.clearChristumNotation(intervalValues[1]);
            String startValue = TimeUtils.clearChristumNotation(intervalValues[0]);

            setEra(intervalValues[1], TimeUtils.END_PLACEHOLDER);
            setEra(intervalValues[0], TimeUtils.START_PLACEHOLDER);

            setMillenniumDate(endValue, TimeUtils.END_PLACEHOLDER);
            setMillenniumDate(startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            String millenniumValue = TimeUtils.clearChristumNotation(preparedValue);

            setEra(value, TimeUtils.END_PLACEHOLDER);
            setEra(value, TimeUtils.START_PLACEHOLDER);

            setMillenniumDate(millenniumValue, TimeUtils.END_PLACEHOLDER);
            setMillenniumDate(millenniumValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setMillenniumDate(String value, String position) {
        Integer millennium = TimePeriodUtils.timePeriodToNumber(value);
        if (millennium != null)
            setMillennium(millennium, position);
    }
}
