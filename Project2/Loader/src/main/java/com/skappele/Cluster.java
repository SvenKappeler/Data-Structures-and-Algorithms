package com.skappele;

import java.util.ArrayList;

/*
 * Cluster class: Represents a cluster in the k-means algorithm. 
 * It should store the cluster's centroid, a list of Business objects belonging to the cluster, 
 * and methods to update the centroid based on the businesses in the cluster.
 * 
 */

public class Cluster {

    private Business centroid;                  // centroid of the cluster
    private ArrayList<Business> businesses;     // list of businesses in the cluster

    public Cluster(Business centroid) {
        this.centroid = centroid;
        this.businesses = new ArrayList<Business>();
    }

    // GETTERS ------------------------------------------------------------------

    public Business getCentroid() {
        return centroid;
    }

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

    public String getClusterName() {
        return centroid.getName();
    }
    
    // SETTERS ------------------------------------------------------------------

    public void setCentroid(Business centroid) {
        this.centroid = centroid;
    }

    public void setBusinesses(ArrayList<Business> businesses) {
        this.businesses = businesses;
    }

    // METHODS ------------------------------------------------------------------

    public void addBusiness(Business business) {
        businesses.add(business);
    }

    public void clearBusinesses() {
        businesses.clear();
    }

    // Calulate the new centroid of the cluster based on the businesses in the cluster
    public void updateCentroid(){
        double totalStars = 0;      // A running total of the stars of the businesses in the cluster
        double totalReviews = 0;    // A running total of the reviews of the businesses in the cluster

        // Calculate the total stars and reviews of the businesses in the cluster
        for (Business business : businesses) {
            totalStars += business.getStars();
            totalReviews += business.getReviewCount();
        }

        double averageStars = totalStars / businesses.size();       // Calculate the average stars of the businesses in the cluster
        double averageReviews = totalReviews / businesses.size();   // Calculate the average reviews of the businesses in the cluster

        centroid.setStars(averageStars);                            // Set the centroid's stars to the average stars of the businesses in the cluster
        centroid.setReviewCount(averageReviews);                    // Set the centroid's reviews to the average reviews of the businesses in the cluster

    }

    // Calculate the euclidiean distance between the centroid and a specfic business
    public double distance(Business business) {
        return centroid.calculateDistance(business);
    }
}
