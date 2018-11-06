package petfinder.site.common.user;

public class ReviewDTO {
    private String user, review;
    private int assignedScore;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getAssignedScore() {
        return assignedScore;
    }

    public void setAssignedScore(int assignedScore) {
        this.assignedScore = assignedScore;
    }
}
