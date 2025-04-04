package ro.webdata.normalization.timespan.ro.model.timePeriod;

import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

public class CenturyModel extends TimePeriodModel {
    public CenturyModel(String original, String value) {
        setCenturyModel(original, value);
    }

    public void setCenturyModel(String original, String value) {
        String preparedValue = TimePeriodUtils.sanitizeTimePeriod(value);
        String[] intervalValues = preparedValue.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1], false);

            Integer endValue = TimePeriodUtils.getEndTime(intervalValues, this.eraStart, false);
            Integer startValue = TimePeriodUtils.getStartTime(intervalValues, this.eraStart, false);

            setCenturyDate(original, endValue, TimeUtils.END_PLACEHOLDER);
            setCenturyDate(original, startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(original, preparedValue, preparedValue, false);

            Integer centuryValue = TimePeriodUtils.timePeriodToNumber(preparedValue, false);

            setCenturyDate(original, centuryValue, TimeUtils.END_PLACEHOLDER);
            setCenturyDate(original, centuryValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setCenturyDate(String original, Integer century, String position) {
        if (century != null) {
            setCentury(original, century, position);
        }
    }
}
