# Data Structures and Algorithms
This repository contains my Data Structures and Algorithm programs for CSC 365.

## Project 1
- A custom hashtable designed for storing Yelp business data.
- Matches Businesses.json to their respective Reviews.json using the Business_id as the key.
- The GUI takes an address and returns the three 'most similar' restaurants.
- The similarity metrics used are keywords and location.

## Project 2
- A custom persistent block-based Hashtable for Yelp business data.
- The project consists of two programs: Loader and GUI.
- Stores businesses as .txt files in folders based on hashed bitstring.
- Performs clustering based on five random businesses.
- Creates five hashtables, one for each cluster, and populates them with the businesses in the respective cluster.
- The GUI, an extension of the GUI in Project 1, also displays the cluster.

## Project 3
- A custom graph with weighted vertices created from deserialized data from Project 2.
- Connects nodes (businesses) to their four closest neighbors using the Haversine Formula.
- Assigns weights to these vertices using TF-IDF.
- Uses Dijkstra's algorithm to print the shortest path from a selected node to the node closest to a cluster center.
