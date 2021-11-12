package by.karelin.business.services;

import by.karelin.business.utils.ErrorCode;
import by.karelin.domain.models.Rating;
import by.karelin.business.dto.Responses.RatingResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IRatingService;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import by.karelin.persistence.repositories.interfaces.IRatingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RatingService implements IRatingService {
    private final IRatingRepository ratingRepository;
    private final IFilmRepository filmRepository;
    private final ModelMapper modelMapper;

    public RatingService(IRatingRepository ratingRepository, IFilmRepository filmRepository, ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
    }

    public ServiceResponse<RatingResponse> getUserRating(Long userId, Long filmId) {
        Rating rating = ratingRepository.getUserRating(userId, filmId);

        if(rating == null){
            return new ServiceResponse<>("The user have not rated this film yet.");
        }

        RatingResponse response = modelMapper.map(rating, RatingResponse.class);

        return new ServiceResponse<>(response);
    }

    public ServiceResponse<Double> setRating(int rate, Long filmId, Long userId) {
        Long newRateId = ratingRepository.setRating(rate, filmId, userId);

        if(newRateId == ErrorCode.RepositoryTransactionError.getValue()){
            return new ServiceResponse<>("Could not set the rating.");
        }

        Double response = filmRepository.updateRating(filmId);

        return new ServiceResponse<>(response);
    }
}
