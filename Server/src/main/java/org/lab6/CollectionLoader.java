package org.lab6;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.lab6.collection.CollectionManager;
import org.lab6.exception.IllegalFieldException;

import java.io.IOException;

public class CollectionLoader {
    public static void loadCollection(String path) {
        try {
            CollectionManager.load(path);
        } catch (JsonMappingException ex) {
            handleJsonMappingException(ex);
        } catch (IOException ex) {
            handleIOException(ex);
        } catch (IllegalFieldException ex) {
            handleIllegalFieldException(ex);
        } catch (SecurityException ex) {
            handleSecurityException(ex);
        } catch (Throwable ex) {
            handleCriticalError(ex);
        }
    }

    private static void handleJsonMappingException(JsonMappingException ex) {
        if (ex.getMessage().contains("No content to map due to end-of-input")) {
            System.err.println("Error: JSON file is empty or incomplete");
        } else {
            throw new RuntimeException(ex);
        }
    }

    private static void handleIOException(IOException ex) {
        System.err.println("Error: Unable to load collection - " + ex.getMessage());
    }

    private static void handleIllegalFieldException(IllegalFieldException ex) {
        System.err.println("Unable to load collection: " + ex.getMessage());
    }

    private static void handleSecurityException(SecurityException ex) {
        System.err.println("Error: Permission denied while loading data. Please check your access rights.");
    }

    private static void handleCriticalError(Throwable ex) {
        System.err.println("Critical error");
        ex.printStackTrace();
    }
}

