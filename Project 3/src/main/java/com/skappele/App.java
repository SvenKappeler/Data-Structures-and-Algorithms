package com.skappele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Hello world!
 */

import java.util.Random;
import java.util.Set;

public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        // Create a new FileParser object
        FileParser fileParser = new FileParser("/Users/svenkappeler/assignment2");
        fileParser.parseFiles();

        // Find the neighbors for each business
        NeighborFinder neighborFinder = new NeighborFinder(fileParser.getBusinesses());
        neighborFinder.findNeighbors();

        for(Business business : neighborFinder.getBusinesses()){
            System.out.println(business.getName());
            System.out.println("Neighbors:");
            for(Business neighbor : business.getNeighbors()){
                System.out.println(neighbor.getName());
            }
            System.out.println();
        }

        ArrayList<Business> businesses = neighborFinder.getBusinesses();

        TFIDF tfidf = new TFIDF();
        for(Business business : businesses){
            for(Business neighbor : business.getNeighbors()){
                double tfidfScore = tfidf.calculateSimilarity(business.getReviews(), neighbor.getReviews());
                System.out.println(neighbor.getName() + tfidfScore);
                business.addNeighborAndWeight(neighbor, tfidfScore);
            }
        }


        // Create disjoint sets
        HashMap<Business, ArrayList<Business>> disjointSets = neighborFinder.createDisjointSets(businesses);
        // Convert businesses to BusinessNodes
        ArrayList<BusinessNode> businessNodes = convertBusinessesToBusinessNodes(businesses);   

        // Build the graph
        Graph graph = new Graph();
        graph.buildGraph(businessNodes);    


        // Find the closest business to the center for each disjoint set
        ArrayList<BusinessNode> closestBusinessNodes = new ArrayList<>();
        for (Business root : disjointSets.keySet()) {
            ArrayList<Business> businessesInSet = disjointSets.get(root);
        
            Business closestBusinessToCenter = null;
            double minDistance = Double.POSITIVE_INFINITY;
        
            for (Business business : businessesInSet) {
                double distance = business.getSimilarityToCentroid();
                if (distance < minDistance) {
                    minDistance = distance;
                    closestBusinessToCenter = business;
                }
            }
        
            // Convert the closest business to a BusinessNode
            BusinessNode closestBusinessNode = convertBusinessToBusinessNode(closestBusinessToCenter);
            closestBusinessNodes.add(closestBusinessNode);
        
            // Choose a random starting node
            ArrayList<BusinessNode> businessNodesInSet = convertBusinessesToBusinessNodes(businessesInSet);

        }

        BusinessNode randomNode = businessNodes.get(2);

        // Loop through random start nodes and closestBusinessNodes
        boolean pathFound = false;

            BusinessNode iterNode = businessNodes.get(0);
            iterNode.printBusinessNode();

            for (int i = 0; i < closestBusinessNodes.size(); i++) {
                BusinessNode closestBusinessNode = closestBusinessNodes.get(i);
                graph.printShortestPathToClosestCentroid(iterNode, closestBusinessNode);
            }

        
    
        System.out.println(closestBusinessNodes.size());
        graph.printAdjacencyList();

        //graph.printNodeDetails(closestBusinessNodes);
        //graph.printAdjacencyList();
    }


    private static BusinessNode convertBusinessToBusinessNode(Business b) {
        String lAndL = b.getLatitude() + "," + b.getLongitude();
        BusinessNode businessNode = new BusinessNode(b.getBusinessId(), b.getName(), b.isClusterCenter(), b.getClusterName(), b.getNeighborAndWeightStringMap(), lAndL, b.getSimilarityToCentroid());
        return businessNode;
    }

    private static ArrayList<BusinessNode> convertBusinessesToBusinessNodes(ArrayList<Business> businesses) {
        ArrayList<BusinessNode> businessNodes = new ArrayList<>();  

        for (Business b : businesses) {
            String lAndL = b.getLatitude() + "," + b.getLongitude();
            BusinessNode businessNode = new BusinessNode(b.getBusinessId(), b.getName(), b.isClusterCenter(), b.getClusterName(), b.getNeighborAndWeightStringMap(), lAndL, b.getSimilarityToCentroid());
            businessNodes.add(businessNode);
        }   

        return businessNodes;
    }
}
