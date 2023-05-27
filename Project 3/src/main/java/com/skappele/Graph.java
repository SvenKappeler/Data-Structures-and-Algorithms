package com.skappele;

import java.io.Serializable;
import java.util.*;

public class Graph implements Serializable {
    private Map<BusinessNode, List<Edge>> adjacencyMap;

    public Graph() {
        adjacencyMap = new HashMap<>();
    }

    public void addNode(BusinessNode node) {
        adjacencyMap.put(node, new ArrayList<>());
    }

    public void addWeightedEdge(BusinessNode source, BusinessNode destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyMap.get(source).add(edge);

        // Since this is a weighted graph, we also need to add an edge in the opposite direction
        Edge reverseEdge = new Edge(destination, source, weight);
        adjacencyMap.get(destination).add(reverseEdge);
    }

    public List<Edge> getNeighbors(BusinessNode node) {
        return adjacencyMap.getOrDefault(node, Collections.emptyList());
    }


    public void buildGraph(List<BusinessNode> businessNodes) {
        // Add nodes to the graph
        for (BusinessNode businessNode : businessNodes) {
            addNode(businessNode);
        }

        // Add weighted edges between business nodes
        for (BusinessNode businessNode : businessNodes) {
            Map<String, Double> neighbors = businessNode.getNeighbors();

            for (Map.Entry<String, Double> entry : neighbors.entrySet()) {

                System.out.println("Adding edge between " + businessNode.getBusinessId() + " and " + entry.getKey());
                String neighborId = entry.getKey();
                double weight = entry.getValue();
                // Locate other business node in the list of business nodes by id
                BusinessNode neighbor = getBusinessNodeById(businessNodes, neighborId);

                    addWeightedEdge(businessNode, neighbor, weight);
                
            }
        }
    }

    // Helper method to retrieve a business node by ID
    private BusinessNode getBusinessNodeById(List<BusinessNode> businessNodes, String businessId) {
        for (BusinessNode businessNode : businessNodes) {
            if (businessNode.getBusinessId().equals(businessId)) {
                return businessNode;
            }
        }
        return null;
    }


    
    public void printNodeDetails(List<BusinessNode> businessNodes) {
        System.out.println("Node details:");
        for (BusinessNode node : businessNodes) {
            System.out.println("Node: " + node.getName() + " (ID: " + node.getBusinessId() + ")");
        }
    }
    
    public void printAdjacencyList() {
        System.out.println("Graph adjacency list:");
        for (Map.Entry<BusinessNode, List<Edge>> entry : adjacencyMap.entrySet()) {
            BusinessNode node = entry.getKey();
            List<Edge> edges = entry.getValue();
            System.out.print(node.getBusinessId() + " -> ");
            for (Edge edge : edges) {
                System.out.print(edge.getDestination().getBusinessId() + " (" + edge.getWeight() + "), ");
            }
            System.out.println();
        }
    }

    // Dijkstra's algorithm to find the shortest path between two nodes
    public void printShortestPathToClosestCentroid(BusinessNode source, BusinessNode target) {
        // Initialize the distance map, visited set, and previous map
        Map<BusinessNode, Double> distanceMap = new HashMap<>();
        Map<BusinessNode, BusinessNode> previousMap = new HashMap<>();
        Set<BusinessNode> visited = new HashSet<>();
    
        // Set the distance of the source node to 0
        distanceMap.put(source, 0.0);
    
        // Priority queue to store nodes based on their distance
        PriorityQueue<BusinessNode> queue = new PriorityQueue<>(Comparator.comparingDouble(distanceMap::get));
    
        // Add the source node to the queue
        queue.offer(source);
    
        while (!queue.isEmpty()) {
            BusinessNode current = queue.poll();
    
            // If the target node is reached, break the loop
            if (current.equals(target)) {
                break;
            }
    
            // Mark the current node as visited
            visited.add(current);
    
            // Process neighbors of the current node
            List<Edge> neighbors = getNeighbors(current);
            for (Edge edge : neighbors) {
                BusinessNode neighbor = edge.getDestination();
    
                // Skip visited nodes
                if (visited.contains(neighbor)) {
                    continue;
                }
    
                double weight = edge.getWeight();
                double neighborDistance = distanceMap.get(current) + weight;
    
                // Update the distance if the new path to the neighbor is shorter
                if (neighborDistance < distanceMap.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    distanceMap.put(neighbor, neighborDistance);
                    previousMap.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
    
        // Build the path
        List<BusinessNode> path = new ArrayList<>();
        BusinessNode currentNode = target;
        while (currentNode != null && previousMap.containsKey(currentNode)) {
            path.add(0, currentNode);
            currentNode = previousMap.get(currentNode);
        }
    
        // Print the path
        if (!path.isEmpty() && path.get(path.size() - 1).equals(target)) {
            System.out.println("Shortest path to target:");
            for (BusinessNode pathNode : path) {
                System.out.println(pathNode.getName());
            }
        } else {
            System.out.println("No path found.");
        }
        
    }
    

    

    

}
