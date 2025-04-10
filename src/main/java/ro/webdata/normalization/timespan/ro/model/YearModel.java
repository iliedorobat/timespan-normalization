package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;
import ro.webdata.normalization.timespan.ro.regex.YearRegex;

public class YearModel extends TimePeriodModel {
    // Used to separate the minus sign from the dash separator "-2 - -14 p.chr"; "-2 p.chr - -14 p.chr"
    private static final String REGEX_AGE_SEPARATOR = "(?<=[\\w\\W&&[^ -]])[ ]*-[ ]*";

    public YearModel(String original, String value, boolean historicalOnly) {
        setYearModel(original, value, historicalOnly);
    }

    private void setYearModel(String original, String value, boolean historicalOnly) {
        String preparedValue = value.replaceAll(YearRegex.YEAR_OR_SEPARATOR, " - ");
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

    private void setDate(String original, String year, String position, boolean historicalOnly) {
        setMillennium(original, year, position, historicalOnly);
        setCentury(original, year, position, historicalOnly);
        setYear(original, year, position, historicalOnly);
    }
}
