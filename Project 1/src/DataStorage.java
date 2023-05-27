import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class DataStorage {


    public static void parseBusinessesJSON(CustomHashTable businesses) throws JsonSyntaxException, IOException{
        // Parse information 
        String filename = "/Users/svenkappeler/Downloads/yelp_dataset/yelp_academic_dataset_business.json";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int restaurantCounter = 0;
            // so long as you do not have 10,000 businesses recorded AND you are not out of businesses to read
            while(restaurantCounter != 10000 && (line = br.readLine()) != null)
            {
                    
                //if business is a restaurant  || NOTE: Could Be More Efficient ((searches the entire line, instead, "Restaurants" will be under key: "categories"))
                if(line.contains("Restaurants")){
                    
                    // parse the JSON object into a Map
                    Map<String, Object> obj = new Gson().fromJson(line, Map.class);
                
                    // create a new Business object from the Map
                    String businessId = (String) obj.get("business_id");
                    String name = (String) obj.get("name");
                    double latitude = (double) obj.get("latitude");
                    double longitude = (double) obj.get("longitude");
                    double stars = (Double) obj.get("stars");
                    String catagories = (String) obj.get("categories");

                    if (catagories.contains("Restaurants")) {

                        // add the Business object to the hashtable
                        Business business = new Business(businessId, name, latitude, longitude, stars);
                        businesses.add(business.getBusinessId(), business);
                        restaurantCounter ++;

                    } else {
                        //System.out.println("Business is not a restaurant");
                    }
                }
            }
        }
    }

    public static void addReviewToBusiness(CustomHashTable businesses) throws FileNotFoundException, IOException{
        // for each business
        String filename = "/Users/svenkappeler/Downloads/yelp_dataset/yelp_academic_dataset_review.json";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            
            // no limit on reviews
            while((line = br.readLine()) != null)
            {        
                // parse the JSON object into a Map
                Map<String, Object> obj = new Gson().fromJson(line, Map.class);
                
                // create a new Business object from the Map
                String businessId = (String) obj.get("business_id");
                String reviewId = (String) obj.get("review_id");
                String text = (String) obj.get("text");
                Business temp = new Business(businessId, reviewId, 0, 0,0);
                
                if (businesses.contains(businessId)) {
                    //System.out.println("Business found");
                    Business business = businesses.get(temp.getBusinessId());
                    if (business != null) {
                        business.addReview(text);
                        business.increaseReviewCount();
                    } else {
                        //System.out.println("Business is null");
                    }
                } else {
                    //System.out.println("Business not found");
                }             
            }
        }
    }
}





