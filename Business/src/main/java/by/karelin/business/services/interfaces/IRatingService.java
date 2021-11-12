package by.karelin.business.services.interfaces;

import by.karelin.business.dto.Responses.RatingResponse;
import by.karelin.business.dto.Responses.ServiceResponse;

public interface IRatingService {
    ServiceResponse<RatingResponse> getUserRating(Long userId, Long filmId);
    ServiceResponse<Double> setRating(int rate, Long filmId, Long userId);
}
