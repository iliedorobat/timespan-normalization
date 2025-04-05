package ro.webdata.normalization.timespan;

import ro.webdata.normalization.timespan.commons.ParamsUtils;
import ro.webdata.normalization.timespan.ro.TimeExpression;
import ro.webdata.echo.commons.File;

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
        TimeExpression timeExpression = new TimeExpression(value, null);

        System.out.println("input value: " + timeExpression.getValue());
        System.out.println("sanitized value: " + timeExpression.getSanitizedValue());
        System.out.println("normalized values: " + timeExpression.getNormalizedValues());
        System.out.println("edges: " + timeExpression.getEdgesValues());
    }

    private static void test() {
        TimeExpression timeExpression = new TimeExpression("1/2 sec. 3 a. chr - sec. 2 p. chr.", null);
//        TimeExpression timeExpression = new TimeExpression("1/2 mil. 5 - sec. i al mil. 4 a.chr.", null);
//        TimeExpression timeExpression = new TimeExpression("4/4 sec.xix. sfârșitul sec.al xix-lea și începutul sec.al xx-lea.", null);
//        TimeExpression timeExpression = new TimeExpression("402-403, 405-406 a. chr.", null);
//        TimeExpression timeExpression = new TimeExpression("1/2 sec. 3 - sec. 1 a. chr.", null);
//        timeExpression = new TimeExpression("epoca modernă", null);
        System.out.println(timeExpression);

//        printFullTimespan(PATH_OUTPUT_ALL_TIMESPAN_FILE);
        // 1779; TOTAL: 39427
//        LidoXmlTimespanAnalysis.printUnknownTimeExpressions(PATH_OUTPUT_ALL_TIMESPAN_FILE);
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
                    System.out.println(new TimeExpression(readLine, "|"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
