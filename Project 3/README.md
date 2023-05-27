# Project 3

Project 3 focuses on creating a custom graph using weighted vertices derived from the deserialized data from Project 2.

## Data Parsing and Preparation

The program begins by parsing the hashtables created in Project 2 and loading the businesses into an ArrayList for ease of access. For this project, a new similarity metric, TF-IDF, is employed as the criteria for clustering differ from those of Project 2.

## Nearest Neighbors and Disjunct Sets

For each business in the ArrayList, the program calculates the top four closest businesses using the Haversine formula applied to the businesses' longitude and latitude coordinates. The resultant groups form 204 disjunct sets. Since not every node can connect to a centroid due to these disjuncts, the program identifies the closest business to the centroid in each disjunct set to serve as the target.

## Creation of Business Nodes and Graph

Following the identification of neighbors and targets, each business, now equipped with a new similarity metric and four neighbors, is transformed into a "Business Node." This transformation helps save memory space by discarding extraneous information. A graph is created consisting of these nodes and edges, with weights assigned based on the TF-IDF scores.

Each node contains four neighbor business_id strings and their corresponding TF-IDF weights to facilitate the addition of edges to the graph.

## Pathfinding with Dijkstra's Algorithm

Dijkstra's Algorithm is implemented to find the shortest path from a given start node to the identified target. The path that was taken will be printed along with the node that was deemed 'closest to the centroid'

