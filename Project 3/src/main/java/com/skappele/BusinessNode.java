package com.skappele;

import java.io.Serializable;
import java.util.Map;


public class BusinessNode implements Serializable{

    // ATTRIBUTES ---------------------------------------------------------------

    private String businessId;
    private String name;
    private boolean isClusterCentroid;
    private String clusterName;
    private Map<String, Double> neighbors;
    private String latitudeLongitude;
    private double similarityToCentroid;

    // CONSTRUCTORS -------------------------------------------------------------

    public BusinessNode(String businessId, String name, boolean isClusterCentroid, String clusterName, Map<String, Double> neighbors, String latitudeLongitude, double similarityToCentroid) {
        this.businessId = businessId;
        this.name = name;
        this.isClusterCentroid = isClusterCentroid;
        this.clusterName = clusterName;
        this.neighbors = neighbors;
        this.latitudeLongitude = latitudeLongitude;
        this.similarityToCentroid = similarityToCentroid;
    }

    // GETTERS ------------------------------------------------------------------

    public String getBusinessId() {
        return businessId;
    }

    public String getName() {
        return name;
    }

    public boolean isClusterCentroid() {
        return isClusterCentroid;
    }

    public String getClusterName() {
        return clusterName;
    }

    public Map<String, Double> getNeighbors() {
        return neighbors;
    }

    public String getLatitudeLongitude() {
        return latitudeLongitude;
    }

    public double getSimilarityToCentroid() {
        return similarityToCentroid;
    }

    // SETTERS ------------------------------------------------------------------

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setClusterCentroid(boolean clusterCentroid) {
        isClusterCentroid = clusterCentroid;
    }

    public void setNeighbors(Map<String, Double> neighbors) {
        this.neighbors = neighbors;
    }

    public void setLatitudeLongitude(String latitudeLongitude) {
        this.latitudeLongitude = latitudeLongitude;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setName(String name) {
        this.name = name;
    }


    // METHODS ------------------------------------------------------------------

    public void printBusinessNode(){
        System.out.println("Business ID: " + businessId);
        System.out.println("Name: " + name);
        System.out.println("Is Cluster Centroid: " + isClusterCentroid);
        System.out.println("Cluster Name: " + clusterName);
        for (Map.Entry<String, Double> entry : neighbors.entrySet()) {
            System.out.println("Neighbor: " + entry.getKey() + " Distance: " + entry.getValue());
        }
        System.out.println("Latitude and Longitude: " + latitudeLongitude);
        System.out.println("Similarity to Centroid: " + similarityToCentroid);
    }

    
}
