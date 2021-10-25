package by.karelin.business.services.interfaces;

import by.karelin.business.dto.Responses.RatingResponse;
import by.karelin.business.dto.Responses.ServiceResponse;

public interface IRatingService {
    ServiceResponse<RatingResponse> GetUserRating(Long userId, Long filmId);
    public ServiceResponse<Double> SetRating(int rate, Long filmId, Long userId);

}
