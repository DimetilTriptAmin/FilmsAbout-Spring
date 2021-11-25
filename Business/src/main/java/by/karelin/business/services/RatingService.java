package by.karelin.business.services;

import by.karelin.business.utils.enums.ErrorCode;
import by.karelin.domain.models.Rating;
import by.karelin.business.dto.Responses.RatingResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IRatingService;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import by.karelin.persistence.repositories.interfaces.IRatingRepository;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RatingService implements IRatingService {
    private final IRatingRepository ratingRepository;
    private final IFilmRepository filmRepository;
    private final ModelMapper modelMapper;
    private static Logger log = Logger.getLogger(RatingService.class.getName());

    public RatingService(IRatingRepository ratingRepository, IFilmRepository filmRepository, ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
    }

    public ServiceResponse<RatingResponse> getUserRating(Long userId, Long filmId) {
        try {
            Rating rating = ratingRepository.getUserRating(userId, filmId);

            if (rating == null) {
                return new ServiceResponse<>(null, HttpStatus.NO_CONTENT);
            }

            RatingResponse response = modelMapper.map(rating, RatingResponse.class);

            return new ServiceResponse<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Server is offline.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ServiceResponse<Double> setRating(int rate, Long filmId, Long userId) {
        try {
            Double newFilmRating = ratingRepository.setRating(rate, filmId, userId);

            if (newFilmRating == ErrorCode.RepositoryTransactionError.getValue()) {
                return new ServiceResponse<>("Could not set the rating.", HttpStatus.NOT_FOUND);
            }

            return new ServiceResponse<>(newFilmRating, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Server is offline.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
