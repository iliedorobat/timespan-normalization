package ro.webdata.normalization.timespan.ro.model;

import com.google.gson.annotations.SerializedName;
import ro.webdata.echo.commons.Const;
import ro.webdata.echo.commons.graph.Namespace;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.TimespanType;

public class DBpediaModel {
    @SerializedName("uri")
    private String uri;

    @SerializedName("label")
    private String label;

    @SerializedName("matchedValue")
    private String matchedValue;

    @SerializedName("matchedType")
    private String matchedType;

    public DBpediaModel(String uri, String matchedType, String matchedValue) {
        if (uri != null) {
            this.uri = uri;
            this.label = uri
                    .replace(Namespace.NS_DBPEDIA_RESOURCE, "")
                    .replace(Const.UNDERSCORE_PLACEHOLDER, " ");
            this.matchedValue = matchedValue;
            this.matchedType = matchedType;
        }
    }

    public static String prepareUri(String era, Integer value, String matchedType) {
        // E.g.: "1/2 mil. 5 - sec. i al mil. 4 a.chr."
        // E.g.: "09 1875"
        if (value == null) {
            return null;
        }

        switch (matchedType) {
            case TimespanType.CENTURY:
                return Namespace.NS_DBPEDIA_RESOURCE + TimeUtils.getOrdinal(value) + Const.DBPEDIA_CENTURY_PLACEHOLDER + DBpediaModel.getEraSuffix(era);
            case TimespanType.MILLENNIUM:
                return Namespace.NS_DBPEDIA_RESOURCE + TimeUtils.getOrdinal(value) + Const.DBPEDIA_MILLENNIUM_PLACEHOLDER + DBpediaModel.getEraSuffix(era);
            case TimespanType.DATE:
            case TimespanType.YEAR:
                return Namespace.NS_DBPEDIA_RESOURCE + String.valueOf(value) + DBpediaModel.getEraSuffix(era);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return this.uri;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other == null || getClass() != other.getClass())
            return false;

        DBpediaModel otherModel = (DBpediaModel) other;
        return this.uri.equals(otherModel.uri);
    }

    @Override
    public int hashCode() {
        return uri.hashCode() + matchedValue.hashCode();
    }

    private static String getEraSuffix(String value) {
        return value.contains(TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                ? Const.UNDERSCORE_PLACEHOLDER + TimeUtils.CHRISTUM_BC_LABEL
                : "";
    }
}
