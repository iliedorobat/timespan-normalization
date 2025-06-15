package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;
import ro.webdata.normalization.timespan.ro.regex.YearRegex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

public class YearModel extends TimePeriodModel {
    // Used to separate the minus sign from the dash separator "-2 - -14 p.chr"; "-2 p.chr - -14 p.chr"
    private static final String REGEX_AGE_SEPARATOR = "(?<=[\\wăâîşșţțĂÂÎŞȘŢȚ\\W&&[^ -]])[ ]*-[ ]*";

    public YearModel() {}

    public YearModel(String original, String value, String regex, boolean historicalOnly) {
        setYearModel(original, value, regex, historicalOnly);
    }

    protected void setYearModel(String original, String value, String regex, boolean historicalOnly) {
        String preparedValue = prepareValue(value, regex);
        String[] intervalValues = preparedValue.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1], true);

            String endYear = TimeUtils.clearChristumNotation(intervalValues[1]);
            String startYear = TimeUtils.clearChristumNotation(intervalValues[0]);

            setDate(original, endYear, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setDate(original, startYear, TimeUtils.START_PLACEHOLDER, historicalOnly);
        } else {
            setEra(original, value, value, true);

            String yearValue = TimeUtils.clearChristumNotation(preparedValue);

            setDate(original, yearValue, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setDate(original, yearValue, TimeUtils.START_PLACEHOLDER, historicalOnly);
        }
    }

    private String prepareValue(String value, String regex) {
        if (regex.equals(YearRegex.YEAR_INTERVAL_PREFIXED)) {
            String preparedValue = value.replaceAll(CASE_INSENSITIVE + REGEX_INTERVAL_PREFIX, "")
                    .replaceAll(CASE_INSENSITIVE + REGEX_INTERVAL_CONJUNCTION, " - ")
                    .trim();
            return TimePeriodUtils.sanitizeTimePeriod(preparedValue);
        }

        String preparedValue = value.replaceAll(CASE_INSENSITIVE + YearRegex.YEAR_OR_SEPARATOR, " - ");
        return TimePeriodUtils.sanitizeTimePeriod(preparedValue);
    }

    private void setDate(String original, String year, String position, boolean historicalOnly) {
        setMillennium(original, year, position, historicalOnly);
        setCentury(original, year, position, historicalOnly);
        setYear(original, year, position, historicalOnly);
    }
}
