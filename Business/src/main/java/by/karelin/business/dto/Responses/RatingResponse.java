package by.karelin.business.dto.Responses;

public class RatingResponse {
    private Long userId;
    private int rate;

    public RatingResponse(Long userId, int rate) {
        this.userId = userId;
        this.rate = rate;
    }

    public RatingResponse() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
