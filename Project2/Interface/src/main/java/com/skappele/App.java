package com.skappele;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */

    public static void main(String[] args) {
        
        // Create a new FileParser object
        FileParser fileParser = new FileParser("/Users/svenkappeler/assignment2");
        // Parse the files
        fileParser.parseFiles();

        // Print the businesses
        fileParser.printBusinesses();

        fileParser.getCount();

        //Similarity similarity = new Similarity(fileParser.getBusinesses());
        //similarity.scoreBusinesses();

        new GUI(fileParser.getBusinesses());
    }
}
