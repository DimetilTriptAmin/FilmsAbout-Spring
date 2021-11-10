package by.karelin.business.services.interfaces;

import by.karelin.persistence.dto.Responses.RatingResponse;
import by.karelin.persistence.dto.Responses.ServiceResponse;

public interface IRatingService {
    ServiceResponse<RatingResponse> getUserRating(Long userId, Long filmId);
    ServiceResponse<Double> setRating(int rate, Long filmId, Long userId);
}
