package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

public class YearModel extends TimePeriodModel {
    // Used to separate the minus sign from the dash separator "-2 - -14 p.chr"; "-2 p.chr - -14 p.chr"
    private static final String REGEX_AGE_SEPARATOR = "(?<=[\\w\\W&&[^ -]])[ ]*-[ ]*";

    public YearModel(String value) {
        setYearModel(value);
    }

    private void setYearModel(String value) {
        // used for cases similar with "anul 13=1800/1801" or with "110/109 a. chr."
        String preparedValue = value.replaceAll("/", " - ");
        String[] intervalValues = preparedValue.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(intervalValues[0], intervalValues[1]);

            String endYear = TimeUtils.clearChristumNotation(intervalValues[1]);
            String startYear = TimeUtils.clearChristumNotation(intervalValues[0]);

            setDate(endYear, TimeUtils.END_PLACEHOLDER);
            setDate(startYear, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(value, value);

            String yearValue = TimeUtils.clearChristumNotation(preparedValue);

            setDate(yearValue, TimeUtils.END_PLACEHOLDER);
            setDate(yearValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setDate(String year, String position) {
        setMillennium(year, position);
        setCentury(year, position);
        setYear(year, position);
    }
}
