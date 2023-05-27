package com.skappele;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class NeighborFinder {


    // ATTRIBUTES ---------------------------------------------------------------

    private ArrayList<Business> businesses;
    private Business[] neighbors = new Business[4];

    // CONSTRUCTOR -------------------------------------------------------------

    public NeighborFinder(ArrayList<Business> businesses) {
        this.businesses = businesses;
    }

    // GETTERS -----------------------------------------------------------------

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

    // METHODS -----------------------------------------------------------------

    public void findNeighbors() {
        // Iterate over each business in businesses
        for (Business mainBusiness : businesses) {
            // Clear the neighbors array for each mainBusiness
            Arrays.fill(neighbors, null);
    
            // For each business, calculate the distance to mainBusiness
            for (Business otherBusiness : businesses) {
                if (!mainBusiness.getBusinessId().equals(otherBusiness.getBusinessId())) {
                    otherBusiness.clearNeighborTempDistance();
                    double distance = mainBusiness.haversineDistance(otherBusiness);

                    // Check if the distance is smaller than any distance in neighbors
                    for (int i = 0; i < neighbors.length; i++) {
                        if (neighbors[i] == null || distance < neighbors[i].getNeighborTempDistance()) {
                            // Shift elements to make room for the new neighbor
                            for (int j = neighbors.length - 1; j > i; j--) {
                                neighbors[j] = neighbors[j - 1];
                            }
                            // Set the new neighbor at the appropriate index
                            otherBusiness.setNeighborTempDistance(distance);
                            neighbors[i] = otherBusiness;
                            break;
                        }
                    }
                }
            }
    
            // Add the neighbors to the mainBusiness
            for (Business neighbor : neighbors) {
                if (neighbor != null) {
                    mainBusiness.addNeighbor(neighbor);

                }
            }
        }
    }

    public int countDisjointSets(ArrayList<Business> businesses) {
        HashMap<Business, Business> parentMap = new HashMap<>();
    
        // Initialize the parentMap for all businesses
        for (Business business : businesses) {
            parentMap.put(business, business);
        }
    
        // Union the neighbors of each business
        for (Business business : businesses) {
            for (Business neighbor : business.getNeighbors()) {
                union(business, neighbor, parentMap);
            }
        }
    
        // Use a HashSet to count the number of unique roots
        HashSet<Business> rootSet = new HashSet<>();
        for (Business business : businesses) {
            Business root = findRoot(business, parentMap);
            rootSet.add(root);
        }
    
        return rootSet.size();
    }
    
    private void union(Business business1, Business business2, HashMap<Business, Business> parentMap) {
        Business root1 = findRoot(business1, parentMap);
        Business root2 = findRoot(business2, parentMap);
    
        if (root1 != root2) {
            parentMap.put(root1, root2);
        }
    }
    
    

    private Business findRoot(Business business, HashMap<Business, Business> parentMap) {
        Business parent = parentMap.get(business);
        if (parent == null || parent == business) {
            return business;
        }
        // Path compression: update the parent to the root
        Business root = findRoot(parent, parentMap);
        parentMap.put(business, root);
        return root;
    }
    


    public HashMap<Business, ArrayList<Business>> createDisjointSets(ArrayList<Business> businesses) {
        HashMap<Business, Business> parentMap = new HashMap<>();
        HashMap<Business, ArrayList<Business>> disjointSets = new HashMap<>();
    
        // Initialize the parentMap for all businesses
        for (Business business : businesses) {
            parentMap.put(business, business);
        }
    
        // Union the neighbors of each business
        for (Business business : businesses) {
            for (Business neighbor : business.getNeighbors()) {
                union(business, neighbor, parentMap);
            }
        }
    
        // Iterate over the businesses and assign them to disjoint sets
        for (Business business : businesses) {
            Business root = findRoot(business, parentMap);
            ArrayList<Business> set = disjointSets.getOrDefault(root, new ArrayList<>());
            set.add(business); // Add the business to the set
            disjointSets.put(root, set);
        }
    
        return disjointSets;
    }
    

    
}