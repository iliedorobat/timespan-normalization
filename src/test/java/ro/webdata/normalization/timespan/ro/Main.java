package test.java.ro.webdata.normalization.timespan.ro;

import main.java.ro.webdata.normalization.timespan.ro.LidoXmlTimespanAnalysis;
import main.java.ro.webdata.normalization.timespan.ro.TimespanUtils;

import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        printTimespan("1/2 sec. iii - sec. ii a. chr.");
        writeTimespan();
    }

    private static void writeTimespan() {
        LidoXmlTimespanAnalysis.writeAll(Const.PATH_INPUT_LIDO_FILES, Const.fileNames, Const.PATH_OUTPUT_ALL_TIMESPAN_FILE);
        LidoXmlTimespanAnalysis.writeUnique(Const.PATH_INPUT_LIDO_FILES, Const.fileNames, Const.PATH_OUTPUT_UNIQUE_TIMESPAN_FILE);
    }

    private static void printTimespan(String text) {
        TreeSet<String> timespanSet = TimespanUtils.getTimespanSet(text);
        String header = getHeader(text);

        System.out.println(header);
        for (String timespan : timespanSet) {
            System.out.println("timespan: " + timespan);
        }
        System.out.println();
    }

    private static String getHeader(String text) {
        String separator = getHeaderSeparator(text.length());
        return separator + "\n" + text + "\n" + separator;
    }

    private static String getHeaderSeparator(int length) {
        StringBuilder separator = new StringBuilder();

        for (int i = 0; i < length; i++) {
            separator.append("-");
        }

        return separator.toString();
    }
}
