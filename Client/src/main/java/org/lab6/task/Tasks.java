package org.lab6.task;


import org.lab6.collection.data.Coordinates;
import org.lab6.collection.data.Location;
import org.lab6.collection.data.Route;
import org.lab6.exception.IllegalFieldException;
import org.lab6.parser.InputManager;

/**
 * Class that contains some util methods for task
 */
public class Tasks {

    private Tasks() {
    }


    /**
     * Requests string from inputManager
     *
     * @param inputManager handles the input
     * @return provided string
     * @throws IllegalArgumentException occurs when the input is incorrect
     */
    public static String getString(InputManager inputManager) throws IllegalArgumentException {
        var response = inputManager.request().trim();
        if (response.isEmpty()) {
            throw new IllegalArgumentException("Input line is empty");
        }
        return response;
    }

    /**
     * Requests integer from inputManager
     *
     * @param inputManager handles the input
     * @return provided integer
     * @throws NumberFormatException occurs when the input is incorrect
     */
    public static int getInteger(InputManager inputManager) throws NumberFormatException {
        var line = getString(inputManager);
        return Integer.parseInt(line);
    }


    /**
     * Requests long from inputManager
     *
     * @param inputManager handles the input
     * @return provided long
     * @throws NumberFormatException occurs when the input is incorrect
     */
    public static long getLong(InputManager inputManager) throws NumberFormatException {
        var line = getString(inputManager);
        return Long.parseLong(line);
    }

    /**
     * Requests float from inputManager
     *
     * @param inputManager handles the input
     * @return provided float
     * @throws NumberFormatException occurs when the input is incorrect
     */
    public static float getFloat(InputManager inputManager) throws NumberFormatException {
        var line = getString(inputManager);
        return Float.parseFloat(line);
    }

    /**
     * Requests double from inputManager
     *
     * @param inputManager handles the input
     * @return provided double
     * @throws NumberFormatException occurs when the input is incorrect
     */
    public static double getDouble(InputManager inputManager) throws NumberFormatException {
        var line = getString(inputManager);
        return Double.parseDouble(line);
    }

    /**
     * Requests coordinates using inputManager
     *
     * @param inputManager handles the input
     * @return provided coordinates
     */
    public static Coordinates getCoordinates(InputManager inputManager) {
        Double x = null;
        Integer y = null;
        while (true) {
            try {
                if (x == null) {
                    System.out.print("Specify X value for coordinates (Double): ");
                    x = getDouble(inputManager);
                }
                if (y == null) {
                    System.out.print("Specify Y value for coordinates (Integer): ");
                    y = getInteger(inputManager);
                }
                return new Coordinates(x, y);
            } catch (IllegalFieldException ex) {
                System.out.println(ex.getMessage());
                x = null;
                y = null;
            } catch (IllegalArgumentException ex) {
                System.out.println("Incorrect input format");
            }
        }
    }

    /**
     * Requests from locations using inputManager
     *
     * @param inputManager handles the input
     * @return provided coordinates
     */
    public static Location.From getFromLocation(InputManager inputManager) {
        Float x = null;
        Integer y = null;
        String name = null;
        while (true) {
            try {
                if (x == null) {
                    System.out.print("Specify X value for 'from' location (Float): ");
                    x = getFloat(inputManager);
                }
                if (y == null) {
                    System.out.print("Specify Y value for 'from' location (Integer): ");
                    y = getInteger(inputManager);
                }
                if (name == null) {
                    System.out.print("Specify Name for 'from' location (String): ");
                    name = getString(inputManager);
                }
                return new Location.From(x, y, name);
            } catch (IllegalFieldException ex) {
                System.out.println(ex.getMessage());
                x = null;
                y = null;
                name = null;
            } catch (IllegalArgumentException ex) {
                System.out.println("Incorrect input format");
            }
        }
    }

    /**
     * Requests to location using inputManager
     *
     * @param inputManager handles the input
     * @return provided coordinates
     */
    public static Location.To getToLocation(InputManager inputManager) {
        Integer x = null;
        Float y = null;
        String name = null;
        while (true) {
            try {
                if (x == null) {
                    System.out.print("Specify X value for 'to' location (Integer): ");
                    x = getInteger(inputManager);
                }
                if (y == null) {
                    System.out.print("Specify Y value for 'to' location (Float): ");
                    y = getFloat(inputManager);
                }
                if (name == null) {
                    System.out.print("Specify Name for 'to' location (String): ");
                    name = getString(inputManager);
                }
                return new Location.To(x, y, name);
            } catch (IllegalFieldException ex) {
                System.out.println(ex.getMessage());
                x = null;
                y = null;
                name = null;
            } catch (IllegalArgumentException ex) {
                System.out.println("Incorrect input format");
            }
        }
    }

    /**
     * Requests route using inputManager
     *
     * @param inputManager handles the input
     * @return provided coordinates


    public static Route getRoute(InputManager inputManager) {
        String name = null;
        Coordinates coordinates = null;
        Location.From from = null;
        Location.To to = null;
        Integer distance = null;
        while (true) {
            try {
                if (name == null) {
                    System.out.print("Specify name for route (String): ");
                    name = getString(inputManager);
                }
                if (coordinates == null) {
                    System.out.println("Specify route coordinates");
                    coordinates = getCoordinates(inputManager);
                }
                if (from == null) {
                    System.out.println("Specify route 'from' location");
                    from = getFromLocation(inputManager);
                }
                if (to == null) {
                    System.out.println("Specify route 'to' location");
                    to = getToLocation(inputManager);
                }
                if (distance == null) {
                    System.out.print("Specify Distance for route (Integer >= " + Route.MIN_DISTANCE + "): ");
                    distance = getInteger(inputManager);
                    if (distance < Route.MIN_DISTANCE) {
                        distance = null;
                        throw new IllegalArgumentException();
                    }
                }
                return new Route(name, coordinates, from, to, distance);
            } catch (IllegalFieldException ex) {
                System.out.println(ex.getMessage());
                name = null;
                coordinates = null;
                from = null;
                to = null;
                distance = null;
            } catch (IllegalArgumentException ex) {
                System.out.println("Incorrect input format");
            }
        }
    }
*/
    /**
     * Requests approval using inputManager
     * @param inputManager handles the input
     * @return true if operation approved, otherwise false
     */
    public static boolean getApproval(String message, InputManager inputManager) {
        System.out.print(message + "? [Yes/No] ");
        while (true) {
            try {
                var response = getString(inputManager);
                if (response.toLowerCase().charAt(0) == 'y') {
                    return true;
                } else if (response.toLowerCase().charAt(0) == 'n') {
                    return false;
                }
            } catch (IllegalArgumentException ignore) {}
        }
    }
}




