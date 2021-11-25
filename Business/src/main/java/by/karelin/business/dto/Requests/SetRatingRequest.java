package by.karelin.business.dto.Requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SetRatingRequest {
    @NotNull(message = "Film ID must be specified")
    private Long filmId;
    @Min(value = 1, message = "Rate must not be less than 1")
    @Max(value = 5, message = "Rate must not be greater than 5")
    private int rate;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }
}
