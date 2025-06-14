package ro.webdata.normalization.timespan.ro.model.imprecise;

import ro.webdata.normalization.timespan.ro.model.YearModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.REGEX_OR;
import static ro.webdata.normalization.timespan.ro.regex.YearRegex.*;
import static ro.webdata.normalization.timespan.ro.regex.imprecise.DatelessRegex.DATELESS_MODEL_X;
import static ro.webdata.normalization.timespan.ro.regex.imprecise.DatelessRegex.DATELESS_UNDATED;

public class DatelessModel extends YearModel {
    private static final String PATTERN = "(" + YEAR_INTERVAL_PREFIXED + ")" + REGEX_OR + "(" + YEAR_INTERVAL_BASE + ")";

    public DatelessModel(String original, String value, String regex, boolean historicalOnly) {
        if (regex.equals(DATELESS_MODEL_X) || regex.equals(DATELESS_UNDATED)) {
            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(original);

            if (matcher.find()) {
                String matchedValue = matcher.group();
                setYearModel(original, matchedValue, regex, historicalOnly);
            } else {
                pattern = Pattern.compile(YEAR_OPTIONS);
                matcher = pattern.matcher(original);

                if (matcher.find()) {
                    String matchedValue = matcher.group();
                    setYearModel(original, matchedValue, regex, historicalOnly);
                }
            }
        }
    }
}
