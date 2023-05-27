package com.skappele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TestGraph {

    public static void main(String[] args) {
        testGraph();
    }

    public static void testGraph() {

        Graph graph = new Graph();

        

        // Create sample nodes
        BusinessNode nodeA = new BusinessNode("A", "Node A", false, "", new HashMap<>(), "0.0,0.0", 0);
        BusinessNode nodeB = new BusinessNode("B", "Node B", false, "", new HashMap<>(), "0.0,0.0", 0);
        BusinessNode nodeC = new BusinessNode("C", "Node C", false, "", new HashMap<>(), "0.0,0.0", 0);
        BusinessNode nodeD = new BusinessNode("D", "Node D", false, "", new HashMap<>(), "0.0,0.0", 0);
        BusinessNode nodeE = new BusinessNode("E", "Node E", false, "", new HashMap<>(), "0.0,0.0", 0);

        ArrayList<BusinessNode> businessNodes = new ArrayList<>();
        businessNodes.add(0, nodeA);
        businessNodes.add(1, nodeB);
        businessNodes.add(2, nodeC);
        businessNodes.add(3, nodeD);
        businessNodes.add(4, nodeE);


    
        graph.buildGraph(businessNodes);

        graph.addWeightedEdge(nodeD, nodeE, 1);

    
        // Test pathfinding
        graph.printShortestPathToClosestCentroid(nodeD, nodeE);
    }
    
}
