package com.skappele;

import java.util.ArrayList;

public class Similarity {

    // ATTRIBUTES ---------------------------------------------------------------

    private ArrayList<Business> businesses = new ArrayList<>();

    // CONSTRUCTORS -------------------------------------------------------------

    public Similarity(ArrayList<Business> businesses){
        this.businesses = businesses;
    }
    

    // Poor Code used from prior project contributed by old partner

    public void scoreBusinesses() {
        int reviewCount = 0;
        double stars = 0;
        int keywordCountTotal = 0;
        int[] keywordSorter = new int[24];
        String[] keywordKey = new String[] {"Delicious", "Tasty", "Fast", "Clean", "Yummy", "Amazing", "Yum", "Fresh", "Perfect", "Great", "Excellent", "Professional", "Perfectly", "Fantastic", "Modern", "Romantic", "Impeccable", "Incredible", "Delightful", "Extraordinary", "Thrilled", "Loved", "Reasonable"};
        String stringCheck = "";
        String keywords = "Delicious delicious Tasty tasty Fast fast Clean clean Yummy yummy Amazing amazing Yum yum Fresh fresh Perfect perfect Great great Excellent excellent Professional professional Perfectly perfectly Fantastic fantastic Modern modern Romantic romantic Impeccable impeccable Incredible incredible Delightful delightful Extraordinary extraordinary Thrilled thrilled Loved loved Reasonable reasonable";
        String[] topThreeKeywords = new String[] {"Error", "Error", "Error"};
        for (Business business : businesses) {
            reviewCount = (int) business.getReviewCount();
            stars = business.getStars();
            //System.out.println("Review Text Here: " + business.getReviews());
            String[] tokenedString = (business.getReviews()).split("\\s+");

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
                            else
                            {
                                //System.out.println("No Match");
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
                    topThreeKeywords [0] = keywordKey[maxIndex];
                    keywordSorter[maxIndex] = 0;

                    max = keywordSorter[0];
                    maxIndex = 0;

                    for(int f = 1; f < 24; f ++) {
                        if(max < keywordSorter[f]) {
                            max = keywordSorter[f];
                            maxIndex = f;
                        }
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

                    topThreeKeywords [2] = keywordKey[maxIndex];
                    keywordSorter[maxIndex] = 0;

                    System.out.println("The top three keywords for this business are: " + topThreeKeywords[0] + ", " + topThreeKeywords[1] + ", " + topThreeKeywords[2] + ".");

                    topThreeKeywords[0] = "";
                    topThreeKeywords[1] = "";
                    topThreeKeywords[2] = "";


                    
                    for(int n = 0; n < 24; n++){
                        keywordSorter[n] = 0;
                    }
                }
            }

        public ArrayList<Business> getBusinesses() {
            return businesses;


        }
    }
