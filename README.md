# Data-Structures-and-Algorithms
A repo for all of my Data Structures and Algorithm programs for CSC 365

# Project 1
A custom hashtable designed for storing the Yelp business data.  
Matches Businesses.json to their respective Reviews.json using the Business_id as the key.  
GUI takes an address and returns the 3 'most similar' resturaunt.  
Similarity Metics: Keywords and Location.  

# Project 2
A custom persistant block-based Hashtable for the Yelp business data.  
This project is composed of 2 programs: Loader and GUI  
Stores businesses as .txt files in folders based on Hashed bitstring  
Performs clustering based on 5 random businesses  
Makes 5 hashtables for each cluster and businesses in cluster  
Extends Project 1 GUI and displays the cluster aswell  

# Project 3
A custom graph with weighted verticies from deserialized Project 2.  
Connects Nodes, businesses, to their closest 4 neighbors using the Haversine Formula.  
These verticies are then given a weight using TD-IDF.  
Using Dykstra's Algorithm the shortest path from a selected node to the closest node to a cluster center is printed.  
