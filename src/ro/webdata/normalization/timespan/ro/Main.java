package ro.webdata.normalization.timespan.ro;

import ro.webdata.echo.commons.File;

import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        String text = "1/2 sec. iii - sec. ii a. chr.";
        TreeSet<String> timespanSet = TimespanUtils.getTimespanSet(text);

        for (String timespan : timespanSet) {
            System.out.println("timespan: " + timespan);
        }

        System.out.println(File.WORKSPACE_DIR);
    }
}
