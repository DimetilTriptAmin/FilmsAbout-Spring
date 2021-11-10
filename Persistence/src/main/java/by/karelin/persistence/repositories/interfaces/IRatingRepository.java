package by.karelin.persistence.repositories.interfaces;

import by.karelin.persistence.dto.Responses.RatingResponse;

public interface IRatingRepository {
    RatingResponse getUserRating(Long userId, Long filmId);
    boolean trySetRating(int rate, Long filmId, Long userId);
}
