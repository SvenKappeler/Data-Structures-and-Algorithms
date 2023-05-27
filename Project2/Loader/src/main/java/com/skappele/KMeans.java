package com.skappele;

import java.util.Random;
import java.util.ArrayList; 
import java.util.List;
/*
 * KMeans class: Implements the k-means clustering algorithm. 
 * This class should include methods to initialize centroids, 
 * assign businesses to the closest centroid, update centroids, 
 * and determine when the algorithm has converged. 
 * It should also have a method to return the sorted businesses based on their cluster assignments.
 * 
 */

 // Assumed to be using 5 clusters


// CONSTRUCTORS -------------------------------------------------------------

public class KMeans {
    private int k;                          // Number of the clusters 
    private ArrayList<Cluster> clusters;    // List of clusters
    private ArrayList<Business> businesses; // List of businesses
    private int maxIterations;              // Maximum number of iterations

    public KMeans(int k, ArrayList<Business> businesses, int maxIterations) {
        this.k = k;
        this.businesses = businesses;
        this.clusters = new ArrayList<Cluster>();
        this.maxIterations = maxIterations;
    }

// GETTERS and INITIALIZERS -------------------------------------------------

    // Get the list of clusters
    public ArrayList<Cluster> getClusters() {
        return clusters;
    }

    // Get the list of businesses
    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

    // Initialize the clusters
    public void initializeClusters() {
        // Create a new list to store the clusters 
        clusters = new ArrayList<Cluster>();
    
        // Create a random number generator
        Random random = new Random();
    
        // Keep track of the selected centroids to ensure no duplicates
        List<Business> selectedCentroids = new ArrayList<>();
    
        // Select k random businesses as initial centroids
        for (int i = 0; i < this.k; i++) {
            Business randomCentroid;

            do {
                // Select a random business from the list
                randomCentroid = businesses.get(random.nextInt(businesses.size()));
            } while (selectedCentroids.contains(randomCentroid));
    
                // Add the random business to the list of selected centroids
                randomCentroid.setName("Cluster " + (i + 1));
                selectedCentroids.add(randomCentroid);
            
                // Create a new cluster with the selected centroid and add it to the list of clusters
                Cluster cluster = new Cluster(randomCentroid);
                clusters.add(cluster);
        }
    }
    

    // METHODS ------------------------------------------------------------------

    // Assign each business to the closest cluster
    public void assignBusinesses() {
        // Clear the businesses from each cluster
        for (Cluster cluster : clusters) {
            cluster.clearBusinesses();
        }

        // Assign each business to the closest cluster
        for (Business business : businesses){
            double minDistance = Double.MAX_VALUE;  // The minimum distance between the business and a cluster
            Cluster closestCluster = null;          // The closest cluster to the business

            // Find the closest cluster to the business
            for (Cluster cluster : clusters) {
                double distance = business.calculateDistance(cluster.getCentroid());

                if (distance < minDistance) {
                    minDistance = distance;
                    closestCluster = cluster;
                }
            }
            // Add the business to the closest cluster
            closestCluster.addBusiness(business);
        }
    }

    // Update the centroids of each cluster
    public void updateCentroids() {
        // Loop through clusters and update the centroid of each cluster
        for (Cluster cluster : clusters) {
            cluster.updateCentroid();
        }
    }

    // Check if the algorithm has converged
    public boolean converged(List<Business> oldCentroids, double threshold) {
        for (int i = 0; i < clusters.size(); i++) {
            Business oldCentroid = oldCentroids.get(i);
            Business newCentroid = clusters.get(i).getCentroid();
            double distance = oldCentroid.calculateDistance(newCentroid);
    
            if (distance > threshold) {
                return false;
            }
        }
        return true;
    }


    // Run the k-means algorithm
    public void run() {
        initializeClusters();
        assignBusinesses();

        for(int i = 0; i < maxIterations; i++) {
            // Save the centroids from the previous iteration
            List<Business> oldCentroids = new ArrayList<>();
            for (Cluster cluster : clusters) {
                oldCentroids.add(cluster.getCentroid());
            }

            // Update the centroids of each cluster
            updateCentroids();

            // Assign businesses to the closest cluster
            assignBusinesses();

            // Check if the algorithm has converged
            if (converged(oldCentroids, 0.0001)) {
                saveClusters();
                break;
            }
        }
    }

    // Save the each cluster to a new Hashtable
    public void saveClusters() {
        int i = 1;
        for (Cluster cluster : clusters) {
            String clusterRootDirectory = "/Users/svenkappeler/assignment2/Cluster" + i + "Output";
            ExtensibleHashTable table = new ExtensibleHashTable(2, 1, clusterRootDirectory);
            table.newGlobalDepthFolder();
            table.newDirectoryFolders();
            for (Business business : cluster.getBusinesses()) {
                table.insert(business);
            }
            i++;
            table.getBusiness("Spq-2DVE8nilFhSFk0jLug");
        }
    }
}