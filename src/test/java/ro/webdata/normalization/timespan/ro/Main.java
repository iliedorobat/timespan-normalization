package test.java.ro.webdata.normalization.timespan.ro;

import main.java.ro.webdata.normalization.timespan.ro.LidoXmlTimespanAnalysis;
import main.java.ro.webdata.normalization.timespan.ro.TimeSanitizeUtils;
import main.java.ro.webdata.normalization.timespan.ro.TimespanUtils;
import test.java.ro.webdata.normalization.timespan.ro.commons.Const;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        String fullTimespan = getFullTimespan("1/2 sec. iii - sec. ii a. chr.");
        System.out.println(fullTimespan);

//        writeTimespan();
//        printFullTimespan(Const.PATH_OUTPUT_ALL_TIMESPAN_FILE);
    }

    private static void writeTimespan() {
        LidoXmlTimespanAnalysis.writeAll(Const.PATH_INPUT_LIDO_FILES, Const.fileNames, Const.PATH_OUTPUT_ALL_TIMESPAN_FILE);
        LidoXmlTimespanAnalysis.writeUnique(Const.PATH_INPUT_LIDO_FILES, Const.fileNames, Const.PATH_OUTPUT_UNIQUE_TIMESPAN_FILE);
    }

    /**
     * Print the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)<br/>
     * !!! <b>writeTimespan</b> will be used to generate the required text files !!!
     * @param inputFullPath The full path to the text file (E.g.: "timespan_all.txt")
     *                      which stores the timespan values
     */
    private static void printFullTimespan(String inputFullPath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFullPath));
            String readLine;

            while((readLine = br.readLine()) != null) {
                if (readLine.length() > 0) {
                    System.out.println(getFullTimespan(readLine));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)
     * @param value The original value
     * @return The prepared output
     */
    private static String getFullTimespan(String value) {
        String sanitized = TimeSanitizeUtils.sanitizeValue(value, null);
        TreeSet<String> set = TimespanUtils.getTimespanSet(value);

        return value
                + Const.CSV_SEPARATOR + sanitized
                + Const.CSV_SEPARATOR + set;
    }
}
