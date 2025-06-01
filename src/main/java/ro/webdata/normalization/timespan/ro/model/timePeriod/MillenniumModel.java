package ro.webdata.normalization.timespan.ro.model.timePeriod;

import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimePeriodRegex;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

public class MillenniumModel extends TimePeriodModel {
    public MillenniumModel(String original, String value, String regex, boolean historicalOnly) {
        setMillenniumModel(original, value, regex, historicalOnly);
    }

    public void setMillenniumModel(String original, String value, String regex, boolean historicalOnly) {
        String preparedValue = prepareValue(value, regex);
        String[] intervalValues = preparedValue.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1], false);

            Integer endValue = TimePeriodUtils.getEndTime(intervalValues, this.eraStart, false);
            Integer startValue = TimePeriodUtils.getStartTime(intervalValues, this.eraStart, false);

            setMillenniumDate(original, endValue, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setMillenniumDate(original, startValue, TimeUtils.START_PLACEHOLDER, historicalOnly);
        } else {
            setEra(original, value, value, false);

            Integer millenniumValue = TimePeriodUtils.timePeriodToNumber(preparedValue, false);

            setMillenniumDate(original, millenniumValue, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setMillenniumDate(original, millenniumValue, TimeUtils.START_PLACEHOLDER, historicalOnly);
        }
    }

    private String prepareValue(String value, String regex) {
        if (regex.equals(TimePeriodRegex.MILLENNIUM_INTERVAL_PREFIXED)) {
            String preparedValue = value.replaceAll(CASE_INSENSITIVE + REGEX_INTERVAL_PREFIX, "")
                    .replaceAll(CASE_INSENSITIVE + REGEX_INTERVAL_CONJUNCTION, " - ")
                    .trim();
            return TimePeriodUtils.sanitizeTimePeriod(preparedValue);
        }

        return TimePeriodUtils.sanitizeTimePeriod(value);
    }

    private void setMillenniumDate(String original, Integer millennium, String position, boolean historicalOnly) {
        if (millennium != null) {
            setMillennium(original, millennium, position, historicalOnly);
        }
    }
}
