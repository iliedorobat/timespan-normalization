package ro.webdata.normalization.timespan.ro.model.timePeriod;

import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimePeriodRegex;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

public class CenturyModel extends TimePeriodModel {
    public CenturyModel(String original, String value, String regex, boolean historicalOnly) {
        setCenturyModel(original, value, regex, historicalOnly);
    }

    public void setCenturyModel(String original, String value, String regex, boolean historicalOnly) {
        String preparedValue = prepareValue(value, regex);
        String[] intervalValues = preparedValue.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1], false);

            Integer endValue = TimePeriodUtils.getEndTime(intervalValues, this.eraStart, false);
            Integer startValue = TimePeriodUtils.getStartTime(intervalValues, this.eraStart, false);

            setCenturyDate(original, endValue, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setCenturyDate(original, startValue, TimeUtils.START_PLACEHOLDER, historicalOnly);
        } else {
            setEra(original, preparedValue, preparedValue, false);

            Integer centuryValue = TimePeriodUtils.timePeriodToNumber(preparedValue, false);

            setCenturyDate(original, centuryValue, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setCenturyDate(original, centuryValue, TimeUtils.START_PLACEHOLDER, historicalOnly);
        }
    }

    private String prepareValue(String value, String regex) {
        if (regex.equals(TimePeriodRegex.CENTURY_INTERVAL_PREFIXED)) {
            String preparedValue = value.replaceAll(CASE_INSENSITIVE + REGEX_INTERVAL_PREFIX, "")
                    .replaceAll(CASE_INSENSITIVE + REGEX_INTERVAL_CONJUNCTION, " - ")
                    .trim();
            return TimePeriodUtils.sanitizeTimePeriod(preparedValue);
        }

        return TimePeriodUtils.sanitizeTimePeriod(value);
    }

    private void setCenturyDate(String original, Integer century, String position, boolean historicalOnly) {
        if (century != null) {
            setCentury(original, century, position, historicalOnly);
        }
    }
}
