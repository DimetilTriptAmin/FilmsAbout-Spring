package by.karelin.business.services;

import by.karelin.business.dto.Responses.RatingResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IRatingService;
import by.karelin.domain.models.Film;
import by.karelin.domain.models.Rating;
import by.karelin.domain.models.User;
import by.karelin.persistence.repositories.IFilmRepository;
import by.karelin.persistence.repositories.IRatingRepository;
import by.karelin.persistence.repositories.IUserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RatingService implements IRatingService {
    private IRatingRepository ratingRepository;
    private IUserRepository userRepository;
    private IFilmRepository filmRepository;

    public RatingService(IRatingRepository ratingRepository, IUserRepository userRepository, IFilmRepository filmRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
    }

    public ServiceResponse<RatingResponse> GetUserRating(Long userId, Long filmId) {
        Optional<Rating> ratingOptional = GetRatingOptional(filmId, userId);

        if (ratingOptional.isEmpty()) {
            return new ServiceResponse<>("User have not rated this film yet");
        }

        RatingResponse ratingResponse =
                new RatingResponse(ratingOptional.get().getId(), ratingOptional.get().getRate());

        return new ServiceResponse<RatingResponse>(ratingResponse);
    }

    public ServiceResponse<Double> SetRating(int rate, Long filmId, Long userId) {

        Optional<Rating> ratingOptional = GetRatingOptional(filmId, userId);

        if (ratingOptional.isEmpty()) {
            return new ServiceResponse<>("User have not rated this film yet");
        }

        Rating currentRating = ratingOptional.get();
        Film film = filmRepository.getById(filmId);

        if (ratingOptional.isEmpty()) {
            User user = userRepository.getById(userId);
            Rating rating = new Rating(rate, film, user);
            ratingRepository.save(rating);
        } else {
            currentRating.setRate(rate);
            ratingRepository.save(currentRating);
        }

        Double newRating = GetFilmRating(filmId);
        film.setRating(newRating);
        filmRepository.save(film);

        return new ServiceResponse<Double>(newRating);
    }

    private Optional<Rating> GetRatingOptional(Long filmId, Long userId) {
        User user = userRepository.getById(userId);
        Film film = filmRepository.getById(filmId);
        Rating rating = new Rating();
        rating.setUser(user);
        rating.setFilm(film);

        Example<Rating> ratingExample = Example.of(rating);

        return ratingRepository.findAll(ratingExample).stream().findFirst();
    }

    private Double GetFilmRating(Long filmId) {
        Film film = filmRepository.getById(filmId);
        Rating rating = new Rating();
        rating.setFilm(film);

        Example<Rating> ratingExample = Example.of(rating);

        Double avgRating = ratingRepository.findAll(ratingExample)
                .stream()
                .map(Rating::getRate)
                .collect(Collectors.averagingDouble(num -> Double.valueOf(num)));

        return avgRating;
    }
}
