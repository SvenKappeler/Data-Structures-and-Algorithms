package com.skappele;

import java.util.*;

public class TFIDF {

    // MAIN METHOD --------------------------------------------------------------
    public double calculateSimilarity(String review1, String review2) {

        // Convert the reviews into TF-IDF vectors
        Map<String, double[]> tfidfVectors = calculateTFIDFVectors(Arrays.asList(review1, review2));

        // Get the TF-IDF vectors for review1 and review2
        double[] vector1 = tfidfVectors.get(review1);
        double[] vector2 = tfidfVectors.get(review2);

        // Calculate the cosine similarity between the TF-IDF vectors, currently a value between 0 and 1
        double similarity = computeCosineSimilarity(vector1, vector2);

        // Multiply by 5 and round to the nearest integer to get a value between 1 and 5
        // This will make the graph easier to draw later on, and will make it easier to compare

        return similarity;
    }


    // HELPER METHODS -----------------------------------------------------------------
    
    private Map<String, double[]> calculateTFIDFVectors(List<String> reviews) {

        // Match freq of term to freq of term in each review
        Map<String, Map<String, Integer>> termFrequencyMap = new HashMap<>();

        // For both of the reviews
        for (String review : reviews) {

            // Build the term frequency map for the review
            Map<String, Integer> frequencyMap = buildTermFrequencyMap(review);
            termFrequencyMap.put(review, frequencyMap);
        }

        // Calculate the IDF (Inverse Document Frequency) for each term
        Map<String, Double> idfMap = calculateIDF(termFrequencyMap);

        // Calculate the TF-IDF vectors for each review
        Map<String, double[]> tfidfVectors = new HashMap<>();
        for (String review : reviews) {
            Map<String, Integer> frequencyMap = termFrequencyMap.get(review);
            double[] tfidfVector = calculateTFIDFVector(frequencyMap, idfMap);
            tfidfVectors.put(review, tfidfVector);
        }

        return tfidfVectors;
    }


    // Tokenize the review and build a frequency map for each term
    private Map<String, Integer> buildTermFrequencyMap(String review) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        // Split the review into words
        String[] words = review.toLowerCase().split("\\W+");

        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }
        return frequencyMap;
    }


    // CALCULATIONS ---------------------------------------------------------

    private Map<String, Double> calculateIDF(Map<String, Map<String, Integer>> termFrequencyMap) {
        Map<String, Double> idfMap = new HashMap<>();
        int numDocuments = termFrequencyMap.size();

        // Calculate the IDF for each term
        for (Map<String, Integer> frequencyMap : termFrequencyMap.values()) {
            for (String term : frequencyMap.keySet()) {
                idfMap.put(term, idfMap.getOrDefault(term, 0.0) + 1);
            }
        }

        // Normalize the IDF values
        for (String term : idfMap.keySet()) {
            double idf = Math.log(numDocuments / (idfMap.get(term) + 1));
            idfMap.put(term, idf);
        }
        return idfMap;
    }

    private double[] calculateTFIDFVector(Map<String, Integer> frequencyMap, Map<String, Double> idfMap) {
        double[] tfidfVector = new double[idfMap.size()];
        int index = 0;

        for (String term : idfMap.keySet()) {
            double tf = frequencyMap.getOrDefault(term, 0);
            double idf = idfMap.get(term);
            tfidfVector[index] = tf * idf;
            index++;
        }

        return tfidfVector;
    }

    private double computeCosineSimilarity(double[] vector1, double[] vector2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
    
        // Calculate the dot product between vector1 and vector2
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            magnitude1 += vector1[i] * vector1[i];
            magnitude2 += vector2[i] * vector2[i];
        }
    
        // Calculate the square root of the magnitudes
        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);
    
        // Calculate the cosine similarity
        double cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
    
        return cosineSimilarity;
    }
}