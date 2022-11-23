package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Collection;
import ro.webdata.echo.commons.Const;
import ro.webdata.echo.commons.graph.Namespace;
import ro.webdata.normalization.timespan.ro.TimeUtils;

import java.util.TreeSet;

public class TimePeriodModel extends TimeModel {
    public TimePeriodModel() {}

    @Override
    public String toString() {
        TreeSet<String> millenniumSet = getMillenniumSet();
        TreeSet<String> centurySet = getCenturySet();
        TreeSet<String> timePeriodSet = new TreeSet<>();
        timePeriodSet.addAll(millenniumSet);
        timePeriodSet.addAll(centurySet);

        return Collection.treeSetToDbpediaString(timePeriodSet);
    }

//    public TreeSet<String> getYearSet() {
//        TreeSet<String> yearSet = new TreeSet<>();
//
//        if (this.yearStart != null && this.yearEnd != null) {
//            pushSameBc(this.eraStart, this.eraEnd, this.yearStart, this.yearEnd, Const.EMPTY_VALUE_PLACEHOLDER, yearSet);
//            pushSameAd(this.eraStart, this.eraEnd, this.yearStart, this.yearEnd, Const.EMPTY_VALUE_PLACEHOLDER, yearSet);
//            pushBcAd(this.eraStart, this.eraEnd, this.yearStart, this.yearEnd, Const.EMPTY_VALUE_PLACEHOLDER, yearSet);
//            pushAdBc(this.eraStart, this.eraEnd, this.yearStart, this.yearEnd, Const.EMPTY_VALUE_PLACEHOLDER, yearSet);
//        }
//
//        return yearSet;
//    }

    public TreeSet<String> getCenturySet() {
        TreeSet<String> centurySet = new TreeSet<>();

        if (this.centuryStart != null && this.centuryEnd != null) {
            pushSameBc(this.eraStart, this.eraEnd, this.centuryStart, this.centuryEnd, Const.DBPEDIA_CENTURY_PLACEHOLDER, centurySet);
            pushSameAd(this.eraStart, this.eraEnd, this.centuryStart, this.centuryEnd, Const.DBPEDIA_CENTURY_PLACEHOLDER, centurySet);
            pushBcAd(this.eraStart, this.eraEnd, this.centuryStart, this.centuryEnd, Const.DBPEDIA_CENTURY_PLACEHOLDER, centurySet);
            pushAdBc(this.eraStart, this.eraEnd, this.centuryStart, this.centuryEnd, Const.DBPEDIA_CENTURY_PLACEHOLDER, centurySet);
        } else {
            centurySet.add(Namespace.NS_REPO_RESOURCE_TIMESPAN_UNKNOWN_CENTURY);
        }

        return centurySet;
    }

    public TreeSet<String> getMillenniumSet() {
        TreeSet<String> millenniumSet = new TreeSet<>();

        if (this.millenniumStart != null && this.millenniumEnd != null) {
            pushSameBc(this.eraStart, this.eraEnd, this.millenniumStart, this.millenniumEnd, Const.DBPEDIA_MILLENNIUM_PLACEHOLDER, millenniumSet);
            pushSameAd(this.eraStart, this.eraEnd, this.millenniumStart, this.millenniumEnd, Const.DBPEDIA_MILLENNIUM_PLACEHOLDER, millenniumSet);
            pushBcAd(this.eraStart, this.eraEnd, this.millenniumStart, this.millenniumEnd, Const.DBPEDIA_MILLENNIUM_PLACEHOLDER, millenniumSet);
            pushAdBc(this.eraStart, this.eraEnd, this.millenniumStart, this.millenniumEnd, Const.DBPEDIA_MILLENNIUM_PLACEHOLDER, millenniumSet);
        } else {
            millenniumSet.add(Namespace.NS_REPO_RESOURCE_TIMESPAN_UNKNOWN_MILLENNIUM);
        }

        return millenniumSet;
    }

    /**
     * Push a time period where the starting era and the ending era are both
     * TimeUtils.CHRISTUM_AD_PLACEHOLDER<br/>
     * E.g.:<br/>
     *      * sec. VI p.Chr - sec. II-lea p.Chr<br/>
     *      * sec. II p.Chr - sec. VI-lea p.Chr
     * @param eraStart The starting era
     * @param eraEnd The ending era
     * @param timeStart The starting time period
     * @param timeEnd The ending time period
     * @param timePlaceholder Placeholder for a time period:<br/>
     *                        * Const.CENTURY_PLACEHOLDER<br/>
     *                        * Const.MILLENNIUM_PLACEHOLDER<br/>
     *                        * Const.EMPTY_VALUE_PLACEHOLDER
     * @param timeSet The set where DBPedia time periods will be stored
     */
    private void pushSameAd(
            String eraStart,
            String eraEnd,
            Integer timeStart,
            Integer timeEnd,
            String timePlaceholder,
            TreeSet<String> timeSet
    ) {
        if (eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)
                && eraEnd.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
            int start = Math.min(timeStart, timeEnd);
            int end = Math.max(timeStart, timeEnd);

            for (int timePeriod = start; timePeriod <= end; timePeriod++) {
                String timeDbpedia = TimeUtils.getOrdinal(timePeriod)
                        + timePlaceholder;
                timeSet.add(timeDbpedia);
            }
        }
    }

    /**
     * Push a time period where the starting era and the ending era are both
     * TimeUtils.CHRISTUM_BC_PLACEHOLDER<br/>
     * E.g.:<br/>
     *      * sec. VI a.Chr - sec. II-lea a.Chr<br/>
     *      * sec. II a.Chr - sec. VI-lea a.Chr
     * @param eraStart The starting era
     * @param eraEnd The ending era
     * @param timeStart The starting time period
     * @param timeEnd The ending time period
     * @param timePlaceholder Placeholder for a time period:<br/>
     *                        * Const.CENTURY_PLACEHOLDER<br/>
     *                        * Const.MILLENNIUM_PLACEHOLDER<br/>
     *                        * Const.EMPTY_VALUE_PLACEHOLDER
     * @param timeSet The set where DBPedia time periods will be stored
     */
    private void pushSameBc(
            String eraStart,
            String eraEnd,
            Integer timeStart,
            Integer timeEnd,
            String timePlaceholder,
            TreeSet<String> timeSet
    ) {
        if (eraStart.equals(TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                && eraEnd.equals(TimeUtils.CHRISTUM_BC_PLACEHOLDER)) {
            int start = Math.max(timeStart, timeEnd);
            int end = Math.min(timeStart, timeEnd);

            for (int timePeriod = start; timePeriod >= end; timePeriod--) {
                String timeDbpedia = TimeUtils.getOrdinal(timePeriod)
                        + timePlaceholder
                        + Const.UNDERSCORE_PLACEHOLDER
                        + TimeUtils.CHRISTUM_BC_LABEL;
                timeSet.add(timeDbpedia);
            }
        }
    }

    private void pushBcAd(
            String eraStart,
            String eraEnd,
            Integer timeStart,
            Integer timeEnd,
            String timePlaceholder,
            TreeSet<String> timeSet
    ) {
        // sec. VI a.Chr - sec. II-lea p.Chr
        if (eraStart.equals(TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                && eraEnd.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
            int start = timeStart;
            int end = 1;
            pushSameBc(
                    TimeUtils.CHRISTUM_BC_PLACEHOLDER,
                    TimeUtils.CHRISTUM_BC_PLACEHOLDER,
                    start,
                    end,
                    timePlaceholder,
                    timeSet
            );

            start = 1;
            end = timeEnd;
            pushSameAd(
                    TimeUtils.CHRISTUM_AD_PLACEHOLDER,
                    TimeUtils.CHRISTUM_AD_PLACEHOLDER,
                    start,
                    end,
                    timePlaceholder,
                    timeSet
            );
        }
    }

    private void pushAdBc(
            String eraStart,
            String eraEnd,
            Integer timeStart,
            Integer timeEnd,
            String timePlaceholder,
            TreeSet<String> timeSet
    ) {
        // sec. II p.Chr - sec. VI-lea a.Chr
        if (eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)
                && eraEnd.equals(TimeUtils.CHRISTUM_BC_PLACEHOLDER)) {
            int start = timeEnd;
            int end = 1;
            pushSameBc(
                    TimeUtils.CHRISTUM_BC_PLACEHOLDER,
                    TimeUtils.CHRISTUM_BC_PLACEHOLDER,
                    start,
                    end,
                    timePlaceholder,
                    timeSet
            );

            start = 1;
            end = timeStart;
            pushSameAd(
                    TimeUtils.CHRISTUM_AD_PLACEHOLDER,
                    TimeUtils.CHRISTUM_AD_PLACEHOLDER,
                    start,
                    end,
                    timePlaceholder,
                    timeSet
            );
        }
    }
}
