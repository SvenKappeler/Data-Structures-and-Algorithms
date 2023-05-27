package com.skappele;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Parse {

    public static void parseJsonFileAndPutIntoHashTable(ExtensibleHashTable table, String filePath) throws ClassNotFoundException {
        System.out.println("Parsing JSON file and putting into hash table...");
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null && counter < 10000) {
                // parse the JSON object into a Map
                Map<String, Object> obj = new Gson().fromJson(line, Map.class);
        
                // create a new Business object from the Map
                String businessId = (String) obj.get("business_id");
                String name = (String) obj.get("name");
                double latitude = Double.parseDouble((String) obj.get("latitude"));
                double longitude = Double.parseDouble((String) obj.get("longitude"));
                double stars = Double.parseDouble((String) obj.get("stars"));
                int reviewCount = Integer.parseInt((String) obj.get("reviewCount"));
                double score = Double.parseDouble((String) obj.get("score"));
                String firstKeyword = (String) obj.get("firstKeyword");
                String secondKeyword = (String) obj.get("secondKeyword");
                String thirdKeyword = (String) obj.get("thirdKeyword");
        
                Business business = new Business(businessId, name, latitude, longitude, stars, reviewCount, score, firstKeyword, secondKeyword, thirdKeyword);

                System.out.println("Inserting business " + counter + " into hash table...");
                table.insert(business);; // put the business into the hash table
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}