package com.skappele;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileParser {

    // ATTRIBUTES --------------------------------------------------------------
    private ArrayList<Business> businesses = new ArrayList<>();
    private String cluster1 = "/Cluster1Output";
    private String cluster2 = "/Cluster2Output";
    private String cluster3 = "/Cluster3Output";
    private String cluster4 = "/Cluster4Output";
    private String cluster5 = "/Cluster5Output";
    private ArrayList<String> clusterDirectories = new ArrayList<>();
    private String fileDirectory;
    int j = 0;

    // CONSTRUCTOR -------------------------------------------------------------

    public FileParser(String fileDirectory) {

        this.fileDirectory = fileDirectory;

    }

    // METHODS -----------------------------------------------------------------

    public void printBusinesses() {
        for (Business business : businesses) {
            System.out.println(business.getName());
        }
    }

    public void parseFiles() {

        // Set the path to the directory containing the cluster folders
        File rootDirectory = new File(fileDirectory);

        clusterDirectories.add(fileDirectory + "/" + cluster1 + "/");
        clusterDirectories.add(fileDirectory + "/" + cluster2 + "/");
        clusterDirectories.add(fileDirectory + "/" + cluster3 + "/");
        clusterDirectories.add(fileDirectory + "/" + cluster4 + "/");
        clusterDirectories.add(fileDirectory + "/" + cluster5 + "/");

        // Loop through each cluster folder
        for(int i = 0; i < clusterDirectories.size(); i++){
            File clusterFolder = new File(clusterDirectories.get(i));
            System.out.println(clusterFolder);
            if (clusterFolder.isDirectory()) {
                File[] files = clusterFolder.listFiles();
                int numFolders = 0;
                for (File file : files) {
                    if (file.isDirectory()) {
                        numFolders++;
                    }
                }
                // Adjust For Folders not starting at 0
                numFolders = numFolders + 1;
                // Get the global depth for this cluster
                String globalDepth = String.valueOf(numFolders);
                File depthFolder = new File(clusterFolder.getPath() + "/depth_" + globalDepth);
                System.out.println(depthFolder);
                // Loop through each folder inside the depth folder
                for (File prefixFolder : depthFolder.listFiles()) {
                    if (prefixFolder.isDirectory()) {
                        System.out.println("Prefix" + prefixFolder);
                        // Loop through each serialized object in this prefix folder
                        for (File serializedObject : prefixFolder.listFiles()) {
                            System.out.println(serializedObject);
                            if (serializedObject.isFile() && serializedObject.getName().endsWith(".txt")) {
                                try {
                                    // Deserialize the list of businesses from the file
                                    byte[] bytes = Files.readAllBytes(serializedObject.toPath());
                                    ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
                                    Pair<String, Business>[] deserializedPairs = (Pair<String, Business>[]) objectIn.readObject();
                                    objectIn.close();

                                    // Create a new ArrayList to store the deserialized businesses
                                    ArrayList<Business> deserializedBusinesses = new ArrayList<>();

                                    // Loop through the pairs and add the businesses to the list
                                    for (Pair<String, Business> pair : deserializedPairs) {
                                        if(pair != null){
                                            System.out.println(pair);
                                            Business business = pair.getValue();
                                            // Set the cluster name for this business
                                            business.setClusterName("cluster" + (i + 1));
                                            // Add this business to the list of businesses
                                            System.out.println(business.getName());
                                            deserializedBusinesses.add(business);
                                            this.j++;
                                        }
                                    }
                                    // Add the deserialized businesses to the main list
                                    businesses.addAll(deserializedBusinesses);

                                } catch (IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

            } else {
                System.out.println("Not a directory");
            }
        }
        System.out.println(j);
    }

    // GETTERS -----------------------------------------------------------------

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

    public void getCount(){
        System.out.println(j);
    }
}







