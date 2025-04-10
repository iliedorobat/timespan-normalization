package ro.webdata.normalization.timespan;

import ro.webdata.echo.commons.File;
import ro.webdata.normalization.timespan.commons.ParamsUtils;
import ro.webdata.normalization.timespan.ro.TimeExpression;
import ro.webdata.normalization.timespan.ro.analysis.LidoXmlAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Demo {
    private static final String PATH_OUTPUT_ALL_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR +
            "timespan_all" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;

    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);
        String value = ParamsUtils.getValue(list, "--expression");
        boolean historicalOnly = ParamsUtils.historicalOnly(list);
        TimeExpression timeExpression = new TimeExpression(value, historicalOnly, null);

        System.out.println(timeExpression.serialize());
    }

    public static void printUnknownTimeExpressions(String inputFullPath) {
        // 1779; TOTAL: 39427
        LidoXmlAnalysis.printUnknownTimeExpressions(inputFullPath);
    }

    /**
     * Print the original value, the value whose Christum notation has been
     * sanitized and the prepared value (the DBpedia links)<br/>
     * !!! <b>writeTimespan</b> will be used to generate the required text files !!!
     * @param inputFullPath The full path to the text file (E.g.: "timespan_all.txt")
     *                      which stores the timespan values (E.g.: PATH_OUTPUT_ALL_TIMESPAN_FILE)
     * @param historicalOnly Flag which specifies whether the Framework will only handle historical
     *                       dates (future dates will be ignored)
     */
    public static void printFullTimespan(String inputFullPath, boolean historicalOnly) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFullPath));
            String readLine;

            while((readLine = br.readLine()) != null) {
                if (!readLine.isEmpty()) {
                    System.out.println(new TimeExpression(readLine, historicalOnly, "|"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
