public class Review {
    private String business_id;
    private String review_id;
    private String text;

    public Review(String review_id, String business_id, String text){
        this.business_id = business_id;
        this.review_id = review_id;
        this.text = text;
    }

    public String getBusinessId(){
        return business_id;
    }

    public String getReviewId(){
        return review_id;
    }

    public String getText(){
        return text;
    }
}
