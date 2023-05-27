public class Business {
    private String business_id;
    private String name;
    private double longitude;
    private double latitude;
    private double stars;
    private String reviews;
    private int reviewCount;
    private double score;
    private String firstKeyword;
    private String secondKeyword;
    private String thirdKeyword;

    public Business(String business_id, String name, double latitude, double longitude, double stars){
        this.business_id = business_id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stars = 0;
        this.reviews = " ";
        this.reviewCount = 0;
        this.stars = stars;
        this.score = score;
        this.firstKeyword = "";
    }

    public Business() {
        this.business_id = "error";
        this.name = "error";
        this.longitude = 999;
        this.latitude = 999;
        this.stars = 999;
        this.reviews = "error";
        this.reviewCount = 999;
        this.score = 999;
        this.firstKeyword = "error";
        this.secondKeyword = "error";
        this.thirdKeyword = "error";
    }

    public void setBusinessId(String businessId){
        this.business_id = businessId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setStars(double stars){
        this.stars = stars;
    }

    public void setReviews(String reviews){
        this.reviews = reviews;
    }

    public void setReviewCount(int reviewCount){
        this.reviewCount = reviewCount;
    }

    public void setScore(double scoreInput){
        this.score = scoreInput;
    }

    public double getScore(){
        return score;
    }

    public void setKeyWords(String keywordOne, String keywordTwo, String keywordThree){
        this.firstKeyword = keywordOne;
        this.secondKeyword = keywordTwo;
        this.thirdKeyword = keywordThree;
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

    public String getBusinessId(){
        return business_id;
    }

    public String getName(){
        return name;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }
    public double getStars(){
        return stars;
    }

    public void addReview(String text){
        reviews += text;
    }
    public String getReviews(){
        return reviews;
    }
    public void increaseReviewCount(){
        reviewCount++;
    }
    public int getReviewCount(){
        return reviewCount;
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
}
