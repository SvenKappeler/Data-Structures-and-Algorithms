package com.skappele;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, ClassNotFoundException
    {
        // Clear the output directory
        clearDirectory("/Users/svenkappeler/assignment2/HashTableOutput");

        Thread.sleep(100);

        ExtensibleHashTable table = new ExtensibleHashTable(2, 1, "/Users/svenkappeler/assignment2/HashTableOutput");
        table.newGlobalDepthFolder();
        table.newDirectoryFolders();

        Parse.parseJsonFileAndPutIntoHashTable(table, "/Users/svenkappeler/Desktop/businessData.json");

        /* 
        table.insert(new Business("Business 1", "Address 1", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 2", "Address 2", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 3", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 4", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 5", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 6", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 7", "Address 1", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 8", "Address 2", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 9", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 10", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 11", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 12", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 13", "Address 1", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 14", "Address 2", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 15", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 16", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 17", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 18", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 19", "Address 1", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 20", "Address 2", 0, 0, 0, 0, 0, null, null, null));
        table.insert(new Business("Business 21", "Address 3", 0, 0, 0, 0, 0, null, null, null));
        */

        table.printDirectory();
        // Print the entire table
        System.out.println(table);
        table.getBusiness("Spq-2DVE8nilFhSFk0jLug");

        System.out.println("Starting KMeans");
        KMeans kMeans = new KMeans(5, table.getAllBusinesses(), 1000000);
        kMeans.run();
        System.out.println("Finished KMeans");
        table.getBusiness("Spq-2DVE8nilFhSFk0jLug");

    }

    public static void clearDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }
    
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}
