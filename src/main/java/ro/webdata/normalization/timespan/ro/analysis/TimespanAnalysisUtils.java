package ro.webdata.normalization.timespan.ro.analysis;

import ro.webdata.echo.commons.File;
import ro.webdata.parser.xml.lido.core.ParserDAO;
import ro.webdata.parser.xml.lido.core.ParserDAOImpl;
import ro.webdata.parser.xml.lido.core.leaf.descriptiveMetadata.DescriptiveMetadata;
import ro.webdata.parser.xml.lido.core.leaf.displayDate.DisplayDate;
import ro.webdata.parser.xml.lido.core.leaf.event.Event;
import ro.webdata.parser.xml.lido.core.leaf.eventDate.EventDate;
import ro.webdata.parser.xml.lido.core.leaf.lido.Lido;
import ro.webdata.parser.xml.lido.core.set.eventSet.EventSet;
import ro.webdata.parser.xml.lido.core.wrap.lidoWrap.LidoWrap;

import java.util.*;
import java.util.stream.Collectors;

public class TimespanAnalysisUtils {
    private TimespanAnalysisUtils() {}

    private static final ParserDAO parserDAO = new ParserDAOImpl();

    /**
     * Extract all timespan to a sorted list
     * @param inputPath The path to input LIDO files
     * @param excludeDemoFiles Flag indicating if demo files are excluded or not
     */
    public static HashMap<String, ArrayList<String>> extractTimespan(String inputPath, String fileName, boolean excludeDemoFiles) {
        HashMap<String, ArrayList<String>> timespanMap = new HashMap<>();

        List<String> fileNames = getFileNames(inputPath, fileName, excludeDemoFiles);
        for (String name : fileNames) {
            addTimespan(inputPath, name, timespanMap);
        }

        for (Map.Entry<String, ArrayList<String>> entry : timespanMap.entrySet()) {
            Collections.sort(entry.getValue());
        }

        return timespanMap;
    }

    /**
     * Consolidate collecting, finding and production related timestamps into a common list
     * @param timespanMap The map containing lists of collecting, finding and production related timestamps
     * @return The consolidated list of timestamps
     */
    public static ArrayList<String> consolidateTimespanMap(HashMap<String, ArrayList<String>> timespanMap) {
        ArrayList<String> consolidatedList = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> entry : timespanMap.entrySet()) {
            consolidatedList.addAll(entry.getValue());
        }

        Collections.sort(consolidatedList);
        return consolidatedList;
    }

    /**
     * Get the list of filenames (fileName == null) or a list containing only a single filename (fileName != null)
     * @param path The path to the LIDO file
     * @param fileName The name of the filename
     * @param excludeDemoFiles Flag indicating if demo files are excluded or not
     * @return The list of filenames
     */
    public static List<String> getFileNames(String path, String fileName, boolean excludeDemoFiles) {
        if (fileName == null) {
            return File.getFileNames(path, File.EXTENSION_XML, excludeDemoFiles);
        }

        return new ArrayList<>(){{
            add(fileName);
        }};
    }

    /**
     * Add timespan to the provided list
     * @param path The path to the LIDO file
     * @param fileName The name of the LIDO file
     * @param timespanMap The map containing the list of time expressions per event type
     */
    private static void addTimespan(String path, String fileName, HashMap<String, ArrayList<String>> timespanMap) {
        String filePath = path + File.FILE_SEPARATOR + fileName;
        addTimespan(filePath, timespanMap);
    }

    /**
     * Add timespan to the provided list
     * @param filePath The path to the LIDO file
     * @param timespanMap The map containing the list of time expressions per event type
     */
    private static void addTimespan(String filePath, HashMap<String, ArrayList<String>> timespanMap) {
        LidoWrap lidoWrap = parserDAO.parseLidoFile(filePath);
        ArrayList<Lido> lidoList = lidoWrap.getLidoList();

        for (Lido lido : lidoList) {
            ArrayList<DescriptiveMetadata> descriptiveMetadataList = lido.getDescriptiveMetadata();

            for (DescriptiveMetadata descriptiveMetadata : descriptiveMetadataList) {
                ArrayList<EventSet> eventSetList = descriptiveMetadata.getEventWrap().getEventSet();
                addEventDateTimespan(eventSetList, timespanMap);
            }
        }
    }

    /**
     * Add event related timespan to the provided list
     * @param eventSetList The list of EventSet items
     * @param timespanMap The map containing the list of time expressions per event type
     */
    private static void addEventDateTimespan(ArrayList<EventSet> eventSetList, HashMap<String, ArrayList<String>> timespanMap) {
        for (EventSet eventSet : eventSetList) {
            Event event = eventSet.getEvent();
            EventDate eventDate = event.getEventDate();
            List<String> termList = event
                    .getEventType()
                    .getTerm()
                    .stream().map(term -> term.getText().trim())
                    .collect(Collectors.toList());

            if (eventDate != null) {
                ArrayList<DisplayDate> displayDateList = eventDate.getDisplayDate();

                for (DisplayDate displayDate : displayDateList) {
                    String timespan = displayDate.getText().toLowerCase();

                    for (String eventType : termList) {
                        if (timespanMap.containsKey(eventType)) {
                            timespanMap.get(eventType).add(timespan);
                        } else {
                            ArrayList<String> arr = new ArrayList<>() {{
                                add(timespan);
                            }};
                            timespanMap.put(eventType, arr);
                        }
                    }
                }
            }
        }
    }
}
