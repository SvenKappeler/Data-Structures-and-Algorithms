//import java.util.*;
import java.io.*;
class CustomHashTable implements java.io.Serializable {

    static final class Node {
	    Object key;
        Business value;
        Node next;
        Node(Object k, Business value, Node n) { key = k; this.value = value; next = n; }
    }

    Node[] table = new Node[8]; // always a power of 2
    int size = 0;

    boolean contains(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
            return true;
        }
        return false;
    }

    void add(Object key, Business value) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
            return;
        }

        table[i] = new Node(key, value, table[i]);
        ++size;
        if ((float)size/table.length >= 0.75f){
            resize();
        }
    }

    void resize() {
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        Node[] newTable = new Node[newCapacity];
        for (int i = 0; i < oldCapacity; ++i) {
            for (Node e = oldTable[i]; e != null; e = e.next) {
            int h = e.key.hashCode();
            int j = h & (newTable.length - 1);
            newTable[j] = new Node(e.key, e.value, newTable[j]);
            }
        }
        table = newTable;
    }

    void remove(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        Node e = table[i], p = null;
        while (e != null) {
            if (key.equals(e.key)) {
            if (p == null)
                table[i] = e.next;
            else
                p.next = e.next;
            break;
            }
            p = e;
            e = e.next;
        }
    }

    void printAll() {
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                System.out.println(e.key);
    }

    private void writeObject(ObjectOutputStream s) throws Exception {
        s.defaultWriteObject();
        s.writeInt(size);
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
            s.writeObject(e.key);
            }
        }
    }

    private void readObject(ObjectInputStream s) throws Exception {
        s.defaultReadObject();
        int n = s.readInt();
        for (int i = 0; i < n; ++i){
            add(s.readObject(), null);
        }
    }

    // a different method for returning a bussiness object from the hashtable
    public Business getBusiness(Object key){
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
            return e.value;
        }
        return null;
    }

    // a method for returning a bussiness object from the hashtable
    public Business get(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            String key1 = (String) key;
            String key2 = (String) e.key;
            if (key1.equals(key2)) {
                return (Business) e.value;
            }
        }
        return null;
    }

    // A method for printing all the business names and their reviews
    public void printAllBusinesses() {
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                if (e.value != null) {
                    System.out.println(" " + e.value.getName() + " " + e.value.getReviewCount() + " " + e.value.getStars() + " Latitude: " + e.value.getLatitude() + " Longitude: " + e.value.getLongitude());
                }
            }
        }
    }
    // getCount method for getting the number of businesses in the hashtable
    public int getCount(){
        return size;
    }


    public void scoreBusinesses() {
        double reviewCount = 0;
        double stars = 0;
        double keywordCountTotal = 0;
        double overallScore = 0;
        int[] keywordSorter = new int[24];
        String[] keywordKey = new String[] {"Delicious", "Tasty", "Fast", "Clean", "Yummy", "Amazing", "Yum", "Fresh", "Perfect", "Great", "Excellent", "Professional", "Perfectly", "Fantastic", "Modern", "Romantic", "Impeccable", "Incredible", "Delightful", "Extraordinary", "Thrilled", "Loved", "Reasonable"};
        String stringCheck = "";
        String keywords = "Delicious delicious Tasty tasty Fast fast Clean clean Yummy yummy Amazing amazing Yum yum Fresh fresh Perfect perfect Great great Excellent excellent Professional professional Perfectly perfectly Fantastic fantastic Modern modern Romantic romantic Impeccable impeccable Incredible incredible Delightful delightful Extraordinary extraordinary Thrilled thrilled Loved loved Reasonable reasonable";
        String[] topThreeKeywords = new String[] {"Error", "Error", "Error"};
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                if (e.value != null) {
                    reviewCount = e.value.getReviewCount();
                    // System.out.println("The review count total is: " + reviewCount);
                    stars = e.value.getStars();
                    // System.out.println("The star count is: " + stars);
                    // System.out.println("Review Text Here: " + e.value.getReviews());
                    String[] tokenedString = (e.value.getReviews()).split("[,|\\s|\\(|\\)|?|.|!]");

                    for(int g = 0; g < tokenedString.length; g++){
                        stringCheck = tokenedString[g];
                        if( keywords.contains(stringCheck)){
                            if(stringCheck.equalsIgnoreCase("Delicious")){
                                keywordSorter[0] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Tasty")){
                                keywordSorter[1] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Fast")){
                                keywordSorter[2] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Clean")){
                                keywordSorter[3] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Yummy")){
                                keywordSorter[4] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Amazing")){
                                keywordSorter[5] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Yum")){
                                keywordSorter[6] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Fresh")){
                                keywordSorter[7] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Perfect")){
                                keywordSorter[8] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Great")){
                                keywordSorter[9] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Professional")){
                                keywordSorter[10] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Excellent")){
                                keywordSorter[11] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Perfectly")){
                                keywordSorter[12] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Fantastic")){
                                keywordSorter[13] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Modern")){
                                keywordSorter[14] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Romantic")){
                                keywordSorter[15] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Impeccable")){
                                keywordSorter[16] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Incredible")){
                                keywordSorter[17] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Delightful")){
                                keywordSorter[18] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Extraordinary")){
                                keywordSorter[19] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Thrilled")){
                                keywordSorter[20] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Loved")){
                                keywordSorter[21] ++;
                            }
                            else if(stringCheck.equalsIgnoreCase("Reasonable")){
                                keywordSorter[22] ++;
                            }
                        }
                    }
                    
                    for(int l = 0; l < 24; l ++)
                    {
                        keywordCountTotal += keywordSorter[l];
                    }

                    int max = keywordSorter[0];
                    int maxIndex = 0;
                    for(int k = 1; k < 24; k ++) {
                        if(max < keywordSorter[k]) {
                            max = keywordSorter[k];
                            maxIndex = k;
                        }
                    }

                    if( max != 0){
                        topThreeKeywords [0] = keywordKey[maxIndex];
                    }
                    else{
                        topThreeKeywords [0] = "Clean";
                    }

                    keywordSorter[maxIndex] = 0;

                    max = keywordSorter[0];
                    maxIndex = 0;

                    for(int f = 1; f < 24; f ++) {
                        if(max < keywordSorter[f]) {
                            max = keywordSorter[f];
                            maxIndex = f;
                        }
                    }

                    if( max != 0){
                        topThreeKeywords [1] = keywordKey[maxIndex];
                    }
                    else{
                        topThreeKeywords [1] = "Fast";
                    }

                    topThreeKeywords [1] = keywordKey[maxIndex];
                    keywordSorter[maxIndex] = 0;

                    max = keywordSorter[0];
                    maxIndex = 0;

                    for(int r = 1; r < 24; r ++) {
                        if(max < keywordSorter[r]) {
                            max = keywordSorter[r];
                            maxIndex = r;
                        }
                    }

                    if( max != 0){
                        topThreeKeywords [2] = keywordKey[maxIndex];
                    }
                    else{
                        topThreeKeywords [2] = "Great";
                    }

                    keywordSorter[maxIndex] = 0;

                    overallScore = stars * ((1/reviewCount) * keywordCountTotal);

                    e.value.setKeyWords(topThreeKeywords[0], topThreeKeywords[1], topThreeKeywords[2]);
                    //System.out.e.value.getKeywords()
                    e.value.setScore(overallScore);

                    System.out.println("Business Name: " + e.value.getName());
                    System.out.println("The top three keywords for this business are: " + e.value.getKeywordOne() + ", " + e.value.getKeywordTwo() + ", " + e.value.getKeywordThree() + ".");
                    System.out.println("Review Count: " + reviewCount + " || Stars: " + stars + " || Keyword Count Total: " + keywordCountTotal);
                    System.out.println("The overall score for this business is: " + e.value.getScore());

                    topThreeKeywords[0] = "";
                    topThreeKeywords[1] = "";
                    topThreeKeywords[2] = "";

                    keywordCountTotal = 0;
                    
                    for(int n = 0; n < 24; n++){
                        keywordSorter[n] = 0;
                    }

                }
            }
        }
    }

    public Business locateClosestBusinesses(double latitudeInput, double longitudeInput)
    {
        Business returnValue = new Business();
        double latitudeTracker = 99999;
        double longitudeTracker = 99999;
        double differenceLongitude = 99999;
        double differenceLatitude = 99999;
        double totalDifference = 99999;
        double totalDifferenceTracker = 99999;
        for(int i = 0; i < table.length; i++) {
            for (Node e = table[i]; e != null; e = e.next) {
                if (e.value != null) {
                    latitudeTracker = e.value.getLatitude();
                    longitudeTracker = e.value.getLongitude();
                    if(((latitudeInput <= 0) && (latitudeTracker <= 0)) || ((latitudeInput > 0) && (latitudeTracker > 0))){
                        if(((longitudeInput <= 0) && (longitudeTracker <= 0)) || ((longitudeInput > 0) && (longitudeTracker > 0))){
                            if(longitudeInput < longitudeTracker){
                                differenceLongitude = java.lang.Math.abs(longitudeTracker - longitudeInput);
                            }
                            else{
                                differenceLongitude = java.lang.Math.abs(longitudeInput - longitudeTracker);
                            }
                            if(latitudeInput < latitudeTracker){
                                differenceLatitude = java.lang.Math.abs(latitudeTracker - latitudeInput);
                            }
                            else{
                                differenceLatitude = java.lang.Math.abs(latitudeInput - latitudeTracker);
                            }
                            totalDifference = differenceLatitude + differenceLongitude;
                            if(totalDifferenceTracker > totalDifference)
                            {
                                returnValue.setBusinessId(e.value.getBusinessId());
                                returnValue.setName(e.value.getName());
                                returnValue.setLongitude(e.value.getLongitude());
                                returnValue.setLatitude(e.value.getLatitude());
                                returnValue.setStars(e.value.getStars());
                                returnValue.setReviews(e.value.getReviews());
                                returnValue.setReviewCount(e.value.getReviewCount());
                                returnValue.setScore(e.value.getScore());
                                returnValue.setKeyWords(e.value.getKeywordOne(), e.value.getKeywordTwo(), e.value.getKeywordThree());
                                totalDifferenceTracker = totalDifference;
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("The closest lat and long is Lat: " + returnValue.getLatitude() + " Long: " + returnValue.getLongitude());
        return returnValue;
    }

    public Business locateSecondClosestBusiness(String firstBusiness, double latitudeInput, double longitudeInput)
    {
        Business returnValue = new Business();
        double latitudeTracker = 99999;
        double longitudeTracker = 99999;
        double differenceLongitude = 99999;
        double differenceLatitude = 99999;
        double totalDifference = 99999;
        double totalDifferenceTracker = 99999;
        for(int i = 0; i < table.length; i++) {
            for (Node e = table[i]; e != null; e = e.next) {
                if (e.value != null) {
                    if (!(e.value.getName().equalsIgnoreCase(firstBusiness))){
                        latitudeTracker = e.value.getLatitude();
                        longitudeTracker = e.value.getLongitude();
                        if(((latitudeInput <= 0) && (latitudeTracker <= 0)) || ((latitudeInput > 0) && (latitudeTracker > 0))){
                            if(((longitudeInput <= 0) && (longitudeTracker <= 0)) || ((longitudeInput > 0) && (longitudeTracker > 0))){
                                if(longitudeInput < longitudeTracker){
                                    differenceLongitude = java.lang.Math.abs(longitudeTracker - longitudeInput);
                                }
                                else{
                                    differenceLongitude = java.lang.Math.abs(longitudeInput - longitudeTracker);
                                }
                                if(latitudeInput < latitudeTracker){
                                    differenceLatitude = java.lang.Math.abs(latitudeTracker - latitudeInput);
                                }
                                else{
                                    differenceLatitude = java.lang.Math.abs(latitudeInput - latitudeTracker);
                                }
                                totalDifference = differenceLatitude + differenceLongitude;
                                if(totalDifferenceTracker > totalDifference)
                                {
                                    returnValue.setBusinessId(e.value.getBusinessId());
                                    returnValue.setName(e.value.getName());
                                    returnValue.setLongitude(e.value.getLongitude());
                                    returnValue.setLatitude(e.value.getLatitude());
                                    returnValue.setStars(e.value.getStars());
                                    returnValue.setReviews(e.value.getReviews());
                                    returnValue.setReviewCount(e.value.getReviewCount());
                                    returnValue.setScore(e.value.getScore());
                                    returnValue.setKeyWords(e.value.getKeywordOne(), e.value.getKeywordTwo(), e.value.getKeywordThree());
                                    totalDifferenceTracker = totalDifference;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("The closest lat and long is Lat: " + returnValue.getLatitude() + " Long: " + returnValue.getLongitude());
        return returnValue;
    }

    public Business locateThirdClosestBusinesses(String firstBusiness, String secondBusiness, double latitudeInput, double longitudeInput)
    {
        Business returnValue = new Business();
        double latitudeTracker = 99999;
        double longitudeTracker = 99999;
        double differenceLongitude = 99999;
        double differenceLatitude = 99999;
        double totalDifference = 99999;
        double totalDifferenceTracker = 99999;
        for(int i = 0; i < table.length; i++) {
            for (Node e = table[i]; e != null; e = e.next) {
                if (e.value != null) {
                    if (!(e.value.getName().equalsIgnoreCase(firstBusiness)) && !(e.value.getName().equalsIgnoreCase(secondBusiness))){
                        latitudeTracker = e.value.getLatitude();
                        longitudeTracker = e.value.getLongitude();
                        if(((latitudeInput <= 0) && (latitudeTracker <= 0)) || ((latitudeInput > 0) && (latitudeTracker > 0))){
                            if(((longitudeInput <= 0) && (longitudeTracker <= 0)) || ((longitudeInput > 0) && (longitudeTracker > 0))){
                                if(longitudeInput < longitudeTracker){
                                    differenceLongitude = java.lang.Math.abs(longitudeTracker - longitudeInput);
                                }
                                else{
                                    differenceLongitude = java.lang.Math.abs(longitudeInput - longitudeTracker);
                                }
                                if(latitudeInput < latitudeTracker){
                                    differenceLatitude = java.lang.Math.abs(latitudeTracker - latitudeInput);
                                }
                                else{
                                    differenceLatitude = java.lang.Math.abs(latitudeInput - latitudeTracker);
                                }
                                totalDifference = differenceLatitude + differenceLongitude;
                                if(totalDifferenceTracker > totalDifference)
                                {
                                    returnValue.setBusinessId(e.value.getBusinessId());
                                    returnValue.setName(e.value.getName());
                                    returnValue.setLongitude(e.value.getLongitude());
                                    returnValue.setLatitude(e.value.getLatitude());
                                    returnValue.setStars(e.value.getStars());
                                    returnValue.setReviews(e.value.getReviews());
                                    returnValue.setReviewCount(e.value.getReviewCount());
                                    returnValue.setScore(e.value.getScore());
                                    returnValue.setKeyWords(e.value.getKeywordOne(), e.value.getKeywordTwo(), e.value.getKeywordThree());
                                
                                    totalDifferenceTracker = totalDifference;
                                }
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("The closest lat and long is Lat: " + returnValue.getLatitude() + " Long: " + returnValue.getLongitude());
        return returnValue;
    }

    public void keywordCounter() {
        
    }

    public int businessScorer() {
        return 0;
    }

    public void printKeywords() {
        for (int i = 0; i < table.length; i++) {
            System.out.print("[" + i + "]");
            for (Node e = table[i]; e != null; e = e.next) {
                System.out.print(" -> " + e.value.getKeywords());
            }
            System.out.println();
        }
    }

}
