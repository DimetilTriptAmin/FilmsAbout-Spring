package by.karelin.persistence.repositories.interfaces;

import by.karelin.domain.models.Rating;

public interface IRatingRepository {
    Rating getUserRating(Long userId, Long filmId);
    Double setRating(int rate, Long filmId, Long userId);
}
