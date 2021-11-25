package by.karelin.webapi.controllers;

import by.karelin.business.dto.Requests.SetRatingRequest;
import by.karelin.business.dto.Responses.RatingResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IRatingService;
import by.karelin.business.utils.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "api/Rating")
public class RatingController {
    private final IRatingService ratingService;
    private final JwtProvider jwtProvider;

    public RatingController(IRatingService ratingService, JwtProvider jwtProvider) {
        this.ratingService = ratingService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping(value = "/{filmId}")
    public ResponseEntity getUserRating(
            @RequestHeader("Authorization") String rawToken,
            @PathVariable Long filmId
    ) {
        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized =  !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<RatingResponse> response = ratingService.getUserRating(userId, filmId);

        if (!response.IsSucceeded()) {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity rateFilm(
            @Valid  @RequestBody SetRatingRequest setRatingRequest,
            @RequestHeader("Authorization") String rawToken
    ) {
        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized =  !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<Double> response =
                ratingService.setRating(setRatingRequest.getRate(), setRatingRequest.getFilmId(), userId);

        if (!response.IsSucceeded()) {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

}
