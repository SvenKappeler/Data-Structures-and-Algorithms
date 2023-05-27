# Project 2

This project comprises two main programs: Loader and GUI. 

## Loader

The Loader deals with the creation and manipulation of blocks and directories, handling four main operations:

1. **Making Blocks:** 
   - Creates a directory folder with a prefix bit name based on directory depth (e.g., `hashtableOutput/depth_5`).
   - Generates folders for every possible bitstring permutation (e.g., `depth_5/00010`).
   
2. **Inserting Businesses into Blocks:**
   - Locates the appropriate .txt block and adds the business object to the block. 
   - If the block does not exist, it creates the file, adds the object, and inserts the block into the directory array.

3. **Splitting Blocks if they are full:**
   - If a block's capacity is full and the localDepth is less than the directoryDepth, it creates two new folders for the increased localDepth and rehashes the old folder contents. 

4. **Rehashing the Blocks into a greater directory depth:**
   - If the localDepth of the block that was attempted to split would be larger than the directory depth, it increases the directoryDepth and reinserts all the businesses into the new depth folder.

**Blocks:** Contain the actual businesses and are represented as a .txt file inside the correct prefix bit folder. They store an amount of businesses inside as a serializable array, which is equal to (localDepth^2).

**Cluster:** Creates 5 clusters based on 5 random businesses:
   - Makes a new hashtable for each cluster.
   - Selects 5 businesses randomly and then iteratively inserts the businesses most similar to the cluster's hashtable. 
   - Similarity metric used here is (Amount of Reviews * Restaurant Rating) to represent "popularity" in two dimensions.
   - Recursively moves the cluster's centroid to the middle of the cluster.
   - This process repeats either 100,000 times or until centroids don't move after recursion.

## GUI 

This is an extension of Project 1 and now includes the cluster to which the business belongs.  
