package ro.webdata.normalization.timespan.ro;

import ro.webdata.echo.commons.Const;
import ro.webdata.echo.commons.graph.Namespace;

public class DBpediaTimeUtils {
    public static String prepareUri(String era, Integer value, String timespanType) {
        // E.g.: "1/2 mil. 5 - sec. i al mil. 4 a.chr."
        // E.g.: "09 1875"
        if (value == null) {
            return null;
        }

        switch (timespanType) {
            case TimespanType.CENTURY:
                return Namespace.NS_DBPEDIA_RESOURCE + TimeUtils.getOrdinal(value) + Const.DBPEDIA_CENTURY_PLACEHOLDER + DBpediaTimeUtils.getEraSuffix(era);
            case TimespanType.MILLENNIUM:
                return Namespace.NS_DBPEDIA_RESOURCE + TimeUtils.getOrdinal(value) + Const.DBPEDIA_MILLENNIUM_PLACEHOLDER + DBpediaTimeUtils.getEraSuffix(era);
            case TimespanType.DATE:
            case TimespanType.YEAR:
                return Namespace.NS_DBPEDIA_RESOURCE + String.valueOf(value) + DBpediaTimeUtils.getEraSuffix(era);
            default:
                return null;
        }
    }

    private static String getEraSuffix(String value) {
        return value.contains(TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                ? Const.UNDERSCORE_PLACEHOLDER + TimeUtils.CHRISTUM_BC_LABEL
                : "";
    }
}
