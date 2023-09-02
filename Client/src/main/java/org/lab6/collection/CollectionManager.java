package org.lab6.collection;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.lab6.collection.data.Location;
import org.lab6.collection.data.Route;
import org.lab6.exception.IllegalFieldException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Class that handles all the operations with collection
 * test
 */
public class CollectionManager {

    private CollectionManager() {
    }

    private static final HashSet<Route> data;
    private static final HashSet<Long> usedIds;
    private static final LocalDateTime creationTime;
    private static final ObjectMapper mapper;
    private static Route minRoute = null;
    private static final String newLine = "\n\r";


    /**
     * Loads and fills the collection from file
     *
     * @param filepath path to file
     * @throws IOException           thrown when there are troubles with file
     * @throws IllegalFieldException thrown when trying to read invalid data
     */
    public static void load(String filepath) throws IOException, IllegalFieldException {
        File dataFile = new File(filepath);
        Scanner scanner = new Scanner(dataFile);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        String serializedData = builder.toString();


        // Deserialize the serializedData into an ArrayList of Route objects
        ArrayList<Route> routes = mapper.readValue(serializedData, new TypeReference<>() {
        });
        data.clear(); // Clear the existing data in the collection

        long skippedElements = 0;
        // Add each Route to the collection, keeping track of skipped elements
        for (var it : routes) {
            if (!add(it)) {
                ++skippedElements;
            }
        }
        System.out.println("Loaded " + data.size() + " element(s)");
        // Print the number of skipped elements, if any
        if (skippedElements > 0) {
            System.out.println("Skipped " + skippedElements + " element(s) to prevent id duplication");
        }
    }

    /**
     * Saves the collection to file
     *
     * @param filepath path to file
     * @throws IOException thrown when there are troubles with file
     */
    public static void save(String filepath) throws IOException {
        String serializedData = mapper.writeValueAsString(data.toArray());
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            byte[] bytes = serializedData.getBytes();
            fos.write(bytes);
        }
    }

    /**
     * Generates string with info about collection
     *
     * @return info about collection
     */
    public static String getInfo() {
        String info = "Collection info" + newLine;
        info += "Collection type: " + data.getClass() + newLine;
        info += "Elements count: " + data.size() + newLine;
        info += "Init time: " + creationTime.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")) + newLine;
        return info;
    }

    /**
     * Generates string with list of all elements
     *
     * @return list of elements in collection
     */
    public static String getElements() {
        if (data.isEmpty()) {
            return "Collection is empty" + newLine;
        }
        var builder = new StringBuilder();
        for (Route route : data) {
            builder.append(route).append(newLine);
        }
        return builder.toString();
    }

    /**
     * Generates free id
     *
     * @return free id
     */
    public static long genId() {
        long value = 0L;
        for (var it = 0L; it <= usedIds.size(); ++it) {
            if (!usedIds.contains(it)) {
                value = it;
                break;
            }
        }
        return value;
    }

    /**
     * Adds new element to collection
     *
     * @param route route to be added
     * @return true if element was added, otherwise false
     */
    public static boolean add(Route route) {
        if (usedIds.contains(route.getId())) {
            return false;
        }
        if (minRoute == null) {
            minRoute = route;
        } else if (route.compareTo(minRoute) < 0) {
            minRoute = route;
        }
        data.add(route);
        usedIds.add(route.getId());
        return true;
    }

    /**
     * Adds new element to collection if it is minimal
     *
     * @param route route to be added
     * @return true if element was added, otherwise false
     */
    public static boolean addIfMin(Route route) {
        if (usedIds.contains(route.getId())) {
            return false;
        }
        if (minRoute != null) {
            if (route.compareTo(minRoute) >= 0) {
                return false;
            }
        }
        minRoute = route;
        data.add(route);
        usedIds.add(route.getId());
        return true;
    }

    /**
     * Removes element by its id
     *
     * @param id id of element
     * @return true if element was removed, otherwise false
     */
    public static boolean remove(long id) {
        if (!data.removeIf(r -> r.getId() == id)) {
            return false;
        }
        usedIds.remove(id);
        if (minRoute.getId() == id) {
            recalculateMinRoute();
        }
        return true;
    }

    /**
     * Removes all the elements greater than provided route
     *
     * @param route route to compare to
     * @return true if any element was removed, otherwise false
     */
    public static boolean removeGreater(Route route) {
        var res = data.removeIf(r -> {
            if (r.compareTo(route) > 0) {
                usedIds.remove(r.getId());
                return true;
            }
            return false;
        });
        recalculateMinRoute();
        return res;
    }

    /**
     * Removes all the elements lower than provided route
     *
     * @param route route to compare to
     * @return true if any element was removed, otherwise false
     */
    public static boolean removeLower(Route route) {
        var res = data.removeIf(r -> {
            if (r.compareTo(route) < 0) {
                usedIds.remove(r.getId());
                return true;
            }
            return false;
        });
        recalculateMinRoute();
        return res;
    }

    /**
     * Groups elements by its Location.To field and shows size of each
     *
     * @return groups with its sizes
     */
    public static String countByTo() {
        if (data.isEmpty()) {
            return "Collection is empty";
        }
        var groups = new HashMap<Location.To, Long>();
        for (var it : data) {
            var to = it.getTo();
            if (!groups.containsKey(to)) {
                groups.put(to, 1L);
            } else {
                groups.put(to, groups.get(to) + 1);
            }
        }
        StringBuilder res = new StringBuilder("Groups:" +newLine);
        for (var it : groups.entrySet()) {
            res.append(it.getKey())
                    .append(", count: ")
                    .append(it.getValue())
                    .append(newLine);
        }
        return res.toString();
    }

    /**
     * Counts number of occurrences of elements with distance greater than provided
     *
     * @param distance distance to compare to
     * @return number of occurrences
     */
    public static int countGreaterDistance(int distance) {
        int count = 0;
        for (var it : data) {
            if (it.getDistance() > distance) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds all the unique values of distances in collection
     *
     * @return set of distances
     */
    public static Set<Integer> uniqueDistances() {
        Set<Integer> distances = new HashSet<>();
        for (var it : data) {
            distances.add(it.getDistance());
        }
        return distances;
    }

    /**
     * Updates element in collection
     *
     * @param route route for replacement
     * @param id    id of element to be updated
     * @return true if element was updated, otherwise false
     */
    public static boolean update(Route route, long id) {
        if (!remove(id)) {
            return false;
        }
        route.setId(id);
        data.add(route);
        return true;
    }

    /**
     * Clears the collection
     */
    public static void clear() {
        data.clear();
        usedIds.clear();
    }

    /**
     * Checks if id is used
     *
     * @param id id to check
     * @return true if id is used, otherwise false
     */
    public static boolean isIdPresent(long id) {
        return usedIds.contains(id);
    }

    /**
     * Update minimal route value
     */
    private static void recalculateMinRoute() {
        minRoute = null;
        for (var it : data) {
            if (minRoute == null) {
                minRoute = it;
                continue;
            }
            if (minRoute.compareTo(it) > 0) {
                minRoute = it;
            }
        }
    }

    static {
        data = new HashSet<>();
        usedIds = new HashSet<>();
        creationTime = LocalDateTime.now();
        mapper = new ObjectMapper(new JsonFactory());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.findAndRegisterModules();
    }
}
