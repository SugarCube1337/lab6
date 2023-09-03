package org.lab6;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.lab6.collection.data.Route;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class StorageManager {

    private String filename;
    private LocalDate initializationDate;
    public static String autosaveName = System.getProperty("java.io.tmpdir") + "autosave.json";
    private HashSet<Route> data = new HashSet<>();

    public StorageManager(String filename) {
        this.filename = filename;
        this.initializationDate = LocalDate.now();
        try {
            if (new File(autosaveName).exists()) {
                System.out.print("You have unsaved data, write '+' to load it: ");
                if ((new Scanner(System.in)).nextLine().equals("+")) {
                    parse(autosaveName);
                    System.out.println("The data was downloaded from an automatic save.");
                } else {
                    new File(autosaveName).delete();
                    parse(filename);
                }
            } else
                parse(filename);
        } catch (Exception exception) {
            System.out.println("Failed to read corrupted data from the file. The collection contains " + data.size() + " elements.");
        }
    }

    /**
     * Convert JSON file to a collection objects
     *
     * @param filename File name
     * @throws IOException Exceptions with saving, i.e. access exceptions, not found exceptions
     */
    public void parse(String filename) throws IOException {
        try (FileReader fileReader = new FileReader(filename)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Route>>() {
            }.getType();
            List<Route> routes = gson.fromJson(fileReader, listType);
            data.addAll(routes);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Get active file name
     *
     * @return File name
     */
    public String getFilename() {
        return filename;
    }


}


