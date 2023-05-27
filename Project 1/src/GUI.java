import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class GUI extends JFrame {
    
    private JLabel addressLabel;
    private JTextField addressField;
    private JButton searchButton;
    private JLabel restaurantLabel;
    private JTextArea restaurantTextArea;
    private JLabel keywordLabel;
    private JTextArea keywordTextArea;
    private CustomHashTable businesses;

    public GUI(CustomHashTable businesses) {
        super("GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 200);
        this.businesses = businesses;


        // create input components
        addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);
        searchButton = new JButton("Search");

        // create output components
        restaurantLabel = new JLabel("Restaurants:");
        restaurantTextArea = new JTextArea(5, 20);
        restaurantTextArea.setEditable(false);
        keywordLabel = new JLabel("Keywords:");
        keywordTextArea = new JTextArea(5, 20);
        keywordTextArea.setEditable(false);

        // create main panel and add components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(addressLabel, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(addressField, c);
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE;
        mainPanel.add(searchButton, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        mainPanel.add(new JSeparator(), c);
        c.gridy = 2;
        c.gridwidth = 1;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        mainPanel.add(new JScrollPane(restaurantTextArea), c);
        c.gridx = 1;
        c.insets = new Insets(5, 10, 5, 10);
        mainPanel.add(new JScrollPane(keywordTextArea), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weightx = 0.5;

        add(mainPanel);

        setVisible(true);

        // add event listeners
        searchButton.addActionListener(e -> {
            try {
                search(addressField.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }


    public class AddressLookup {
        private static final String API_URL = "https://nominatim.openstreetmap.org/search";
        private static final String USER_AGENT = "Mozilla/5.0";
        
        public static void main(String[] args) throws IOException {
            String address = "";
            String url = String.format("%s?q=%s&format=json", API_URL, address);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(con.getInputStream());
                String responseBody = scanner.useDelimiter("\\A").next();
                scanner.close();

                // Parse the response body to extract the longitude and latitude values
                System.out.println(responseBody);
            } else {
                System.out.println("Failed to lookup address");
            }
        }
    }

    public void search(String address) throws IOException {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
    
        // Construct the URL with the required parameters for the Nominatim API
        String url = String.format("https://nominatim.openstreetmap.org/search?format=json&q=%s&addressdetails=1&limit=1", encodedAddress);
    
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", AddressLookup.USER_AGENT);
        int responseCode = con.getResponseCode();


        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(con.getInputStream());
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();
    
            // Parse the response body with Gson
            JsonArray responseJson = new Gson().fromJson(responseBody, JsonArray.class);
            if (responseJson.size() > 0) {
                // Get the first result from the JSON response
                JsonObject firstResult = responseJson.get(0).getAsJsonObject();

                // Get the latitude and longitude from the first result
                String latitude = firstResult.get("lat").getAsString();
                String longitude = firstResult.get("lon").getAsString();


                Business outputOne = new Business();
                Business outputTwo = new Business();
                Business outputThree = new Business();

                Double latitudeDouble = Double.parseDouble(latitude);
                Double longitudeDouble = Double.parseDouble(longitude);
        

                
        
                outputOne = businesses.locateClosestBusinesses(latitudeDouble, longitudeDouble);
                outputTwo = businesses.locateSecondClosestBusiness(outputOne.getName(), latitudeDouble, longitudeDouble);
                outputThree = businesses.locateThirdClosestBusinesses(outputOne.getName(), outputTwo.getName(), latitudeDouble, longitudeDouble);

                System.out.println("The First closest business is: " + outputOne.getName());
                System.out.println("The Second closest business is: " + outputTwo.getName());
                System.out.println("The Third closest business is: " + outputThree.getName());    

                restaurantTextArea.setText(outputOne.getName() + "\n" + outputTwo.getName() + "\n" + outputThree.getName());
                keywordTextArea.setText(outputOne.getKeywords() + "\n" + outputTwo.getKeywords() + "\n" + outputThree.getKeywords());
                

            } else {
                System.out.println("No results found");
            }
        } else {
            System.out.println("Failed to lookup address");
        }
    }
    
    
    
    


    public class MyFrame extends JFrame {
    public MyFrame() {
        // set the layout manager for the frame
        setLayout(new BorderLayout());

        // create and add components to the frame
        JButton button1 = new JButton("Button 1");
        add(button1, BorderLayout.NORTH);

        JButton button2 = new JButton("Button 2");
        add(button2, BorderLayout.WEST);

        JButton button3 = new JButton("Button 3");
        add(button3, BorderLayout.CENTER);

        // set the size and visibility of the frame
        setSize(800, 800);
        setVisible(true);
    }
}


}
