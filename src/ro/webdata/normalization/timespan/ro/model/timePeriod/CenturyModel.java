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
            setEra(intervalValues[0], intervalValues[1]);

            Integer endValue = TimePeriodUtils.getEndTime(intervalValues, this.eraStart);
            Integer startValue = TimePeriodUtils.getStartTime(intervalValues, this.eraStart);

            setCenturyDate(endValue, TimeUtils.END_PLACEHOLDER);
            setCenturyDate(startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(value, value);

            Integer centuryValue = TimePeriodUtils.timePeriodToNumber(preparedValue);

            setCenturyDate(centuryValue, TimeUtils.END_PLACEHOLDER);
            setCenturyDate(centuryValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setCenturyDate(Integer century, String position) {
        if (century != null) {
            int millennium = TimeUtils.centuryToMillennium(century);
            setMillennium(millennium, position);
            setCentury(century, position);
        }
    }
}
