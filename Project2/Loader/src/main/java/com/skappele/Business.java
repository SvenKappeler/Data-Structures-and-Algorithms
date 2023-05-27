package com.skappele;

import java.io.Serializable;
import java.util.ArrayList;

public class Business implements Serializable{

    // ATTRIBUTES ---------------------------------------------------------------

    private String businessId;
    private String name;
    private String firstKeyword;
    private String secondKeyword;
    private String thirdKeyword;
    private double latitude;
    private double longitude;
    private double stars;
    private double reviewCount;
    private double score;
    private String bitString;
    private String prefix;
    private String filePath;
    private String blockBits;
    private String reviews;
    private String clusterName;


    // CONSTRUCTORS -------------------------------------------------------------

    public Business(String businessId, String name, double latitude, double longitude, double stars, double reviewCount, double score, String firstKeyword, String secondKeyword, String thirdKeyword, String reviews){
        this.businessId = businessId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stars = stars;
        this.reviewCount = reviewCount;
        this.score = score;
        this.firstKeyword = firstKeyword;
        this.secondKeyword = secondKeyword;
        this.thirdKeyword = thirdKeyword;
        this.reviews = reviews;
    }

    // GETTERS ------------------------------------------------------------------

    public String getBusinessId(){
        return businessId;
    }

    public String getName(){
        return name;
    }

    public String getKeywordOne(){
        return firstKeyword;
    }

    public String getKeywordTwo(){
        return secondKeyword;
    }

    public String getKeywordThree(){
        return thirdKeyword;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getStars(){
        return stars;
    }

    public double getReviewCount(){
        return reviewCount;
    }

    public double getScore(){
        return score;
    }

    public String getBlockBits(){
        return bitString;
    }

    public String getPrefix(){
        return prefix;
    }

    public String getFilePath(){
        return filePath;
    }

    public String getReviews(){
        return reviews;
    }

    public String getClusterName(){
        return clusterName;
    }

    // SETTERS ------------------------------------------------------------------

    public void setBusinessId(String businessId){
        this.businessId = businessId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setFirstKeyword(String firstKeyword){
        this.firstKeyword = firstKeyword;
    }

    public void setSecondKeyword(String secondKeyword){
        this.secondKeyword = secondKeyword;
    }

    public void setThirdKeyword(String thirdKeyword){
        this.thirdKeyword = thirdKeyword;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setStars(double stars){
        this.stars = stars;
    }

    public void setReviewCount(double averageReviews){
        this.reviewCount = averageReviews;
    }

    public void setScore(double score){
        this.score = score;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setFirst(String keyword) {
        this.firstKeyword = keyword;
    }

    public void setSecond(String keyword) {
        this.secondKeyword = keyword;
    }

    public void setThird(String keyword) {
        this.thirdKeyword = keyword;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public void setKeyWords(String first, String second, String third) {
        this.firstKeyword = first;
        this.secondKeyword = second;
        this.thirdKeyword = third;
    }
 


    // METHODS ------------------------------------------------------------------

    // A method for hashing the business id to a 32-bit binary string
    
    public String getBitString(){
        // Hash the business id to a 32-bit integer
        int hash = businessId.hashCode();

        // Convert the integer to a 32-bit binary string
        String bitString = Integer.toBinaryString(hash);
        this.bitString = bitString;

        // Pad the binary string with leading zeros
        while(bitString.length() < 32){
            bitString = "0" + bitString;
        }
        return bitString;
    }

    public double[] getCoordinates(){
        double[] coordinates = new double[2];
        coordinates[0] = longitude;
        coordinates[1] = latitude;
        return coordinates;
    }

    public String getKeywords(){
        return firstKeyword + " " + secondKeyword + " " + thirdKeyword;
    }

    // A method for determining the distance between two businesses using reviewCount and stars

    public double calculateDistance(Business other){
        double reviewCountDiff = this.reviewCount - other.reviewCount;
        double starsDiff = this.stars - other.stars;

        // Euclidean distance formula
        double distance = Math.sqrt(Math.pow(reviewCountDiff, 2) + Math.pow(starsDiff, 2));
        return distance;
    }
    
}
