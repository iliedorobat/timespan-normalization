package ro.webdata.normalization.timespan.ro.model.timePeriod;

import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

public class CenturyModel extends TimePeriodModel {
    public CenturyModel(String value) {
        setCenturyModel(value);
    }

    public void setCenturyModel(String value) {
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

            setCenturyDate(endValue, TimeUtils.END_PLACEHOLDER);
            setCenturyDate(startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            String centuryValue = TimeUtils.clearChristumNotation(preparedValue);

            setEra(value, TimeUtils.END_PLACEHOLDER);
            setEra(value, TimeUtils.START_PLACEHOLDER);

            setCenturyDate(centuryValue, TimeUtils.END_PLACEHOLDER);
            setCenturyDate(centuryValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setCenturyDate(String value, String position) {
        Integer century = TimePeriodUtils.timePeriodToNumber(value);
        if (century != null) {
            int millennium = (century / 10) + 1;
            setMillennium(millennium, position);
            setCentury(century, position);
        }
    }
}
