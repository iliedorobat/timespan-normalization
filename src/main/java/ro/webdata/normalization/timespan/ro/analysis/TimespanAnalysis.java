package ro.webdata.normalization.timespan.ro.analysis;

import ro.webdata.echo.commons.Const;
import ro.webdata.echo.commons.File;
import ro.webdata.echo.commons.Print;
import ro.webdata.normalization.timespan.commons.EnvConst;
import ro.webdata.normalization.timespan.ro.TimeExpression;

import java.util.*;

public class TimespanAnalysis {
    private TimespanAnalysis() {}

    private static final String OPERATION_END = "----- END -----";
    private static final String OPERATION_START = "----- START -----";

    /**
     * Extract all time expressions from LIDO files
     * @param outputFullPath The full path of the output file
     * @param inputPath The path to input LIDO files
     * @param excludeDemoFiles Flag indicating if demo files are excluded or not
     * @param onlyUnique Flag indicating whether only unique time expressions are extracted
     */
    public static void writeDetails(String inputPath, String outputFullPath, boolean excludeDemoFiles, boolean onlyUnique) {
        List<String> fileNames = File.getFileNames(inputPath, File.EXTENSION_XML, excludeDemoFiles);

        for (String fileName : fileNames) {
            write(inputPath, outputFullPath, fileName, excludeDemoFiles, onlyUnique);
        }
    }

    /**
     * Extract all time expressions from LIDO files
     * @param outputFullPath The full path of the output file
     * @param inputPath The path to input LIDO files
     * @param excludeDemoFiles Flag indicating if demo files are excluded or not
     * @param onlyUnique Flag indicating whether only unique time expressions are extracted
     */
    public static void write(String inputPath, String outputFullPath, boolean excludeDemoFiles, boolean onlyUnique) {
        write(inputPath, outputFullPath, null, excludeDemoFiles, onlyUnique);
    }

    // Extract all time expressions from LIDO files
    public static void write(String inputPath, String outputFullPath, String fileName, boolean excludeDemoFiles, boolean onlyUnique) {
        Print.operation(OPERATION_START, EnvConst.SHOULD_PRINT);
        System.out.println("File: " + (fileName != null ? fileName : "ALL") + "\n");

        Map<String, List<String>> timespanMap = TimespanAnalysisUtils.extractTimespan(inputPath, fileName, excludeDemoFiles);

        for (Map.Entry<String, List<String>> entry : timespanMap.entrySet()) {
            String path = prepareOutputPath(outputFullPath, fileName, entry.getKey());
            List<String> list = entry.getValue();
            write(list, path, onlyUnique);
        }

        String path = prepareFilePath(outputFullPath, fileName);
        List<String> consolidatedTimespanMap = TimespanAnalysisUtils.consolidateTimespanMap(timespanMap);
        write(consolidatedTimespanMap, path, onlyUnique);

        Print.operation(OPERATION_END, EnvConst.SHOULD_PRINT);
    }

    private static String prepareOutputPath(String outputFullPath, String fileName, String eventType) {
        String path = fileName != null
                ? prepareFilePath(outputFullPath, fileName)
                : outputFullPath;

        return eventType != null
                ? appendFileSuffix(path, eventType)
                : path;
    }

    // Extract all time expressions from LIDO files
    private static void write(List<String> initTimespanList, String outputFullPath, boolean onlyUnique) {
        List<String> timespanList = initTimespanList;
        if (onlyUnique) {
            Set<String> set = new TreeSet<>(timespanList);
            timespanList = new ArrayList<>(set);
        }

        File.write(timespanList, outputFullPath, false);

        List<String> timeExpressions = toTimeExpressions(timespanList);
        String csvOutputFullPath = outputFullPath.replaceAll("\\.[a-zA-Z]*", File.EXTENSION_SEPARATOR + File.EXTENSION_CSV);
        File.write(timeExpressions, csvOutputFullPath, false);
    }

    /**
     * Append the filename suffix to the input filePath.
     * E.g.: /usr/local/my_file.txt => /usr/local/my_file_suffix.txt
     *
     * @param filePath The full path
     * @param suffix A word added at the end of the target word
     * @return The new path
     */
    private static String appendFileSuffix(String filePath, String suffix) {
        String SUFFIX = suffix == null ? "" : suffix.toLowerCase();
        String newFilePath = filePath.replaceAll("\\.[a-zA-Z]*", Const.UNDERSCORE_PLACEHOLDER + SUFFIX);
        String extension = filePath.replaceAll(".*\\.", "");

        return newFilePath + File.EXTENSION_SEPARATOR + extension;
    }

    private static List<String> toTimeExpressions(List<String> strTimeExpressions) {
        List<String> timeExpressions = new ArrayList<>(){{
            add(TimeExpression.getHeaders());
        }};

        for (String str : strTimeExpressions) {
            TimeExpression timeExpression = new TimeExpression(str, "|");
            timeExpressions.add(timeExpression.toString());
        }

        return timeExpressions;
    }

    private static String prepareFilePath(String outputFullPath, String fileName) {
        if (fileName == null) {
            return outputFullPath;
        }

        String name = fileName
                .replaceAll("inp-clasate-", "")
                .replaceAll("-2014-02-02.xml", "")
                .replaceAll("-2014-02-03-7.xml", "");

        String path = outputFullPath.substring(0, outputFullPath.lastIndexOf(File.FILE_SEPARATOR) + 1);
        String filePostfix = outputFullPath.substring(outputFullPath.lastIndexOf(File.FILE_SEPARATOR) + 1);

        return path + name + File.FILE_SEPARATOR + filePostfix;
    }
}
