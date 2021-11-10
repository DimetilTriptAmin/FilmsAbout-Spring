package by.karelin.business.services;

import by.karelin.persistence.dto.Responses.RatingResponse;
import by.karelin.persistence.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IRatingService;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import by.karelin.persistence.repositories.interfaces.IRatingRepository;
import org.springframework.stereotype.Component;

@Component
public class RatingService implements IRatingService {
    private final IRatingRepository ratingRepository;
    private final IFilmRepository filmRepository;

    public RatingService(IRatingRepository ratingRepository, IFilmRepository filmRepository) {
        this.ratingRepository = ratingRepository;
        this.filmRepository = filmRepository;
    }

    public ServiceResponse<RatingResponse> getUserRating(Long userId, Long filmId) {
        RatingResponse response = ratingRepository.getUserRating(userId, filmId);

        if(response == null){
            return new ServiceResponse<RatingResponse>("The user have not rated this film yet.");
        }

        return new ServiceResponse<RatingResponse>(response);
    }

    public ServiceResponse<Double> setRating(int rate, Long filmId, Long userId) {
        boolean setRatingFailed = !ratingRepository.trySetRating(rate, filmId, userId);

        if(setRatingFailed){
            return new ServiceResponse<>("Could not set the rating.");
        }

        Double response = filmRepository.updateRating(filmId);

        return new ServiceResponse<Double>(response);
    }
}
