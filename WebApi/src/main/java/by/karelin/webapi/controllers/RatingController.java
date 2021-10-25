package by.karelin.webapi.controllers;

import by.karelin.business.dto.Requests.SetRatingRequest;
import by.karelin.business.dto.Responses.RatingResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/Rating")
public class RatingController {
    private IRatingService ratingService;

    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value="/getUserRating{filmId}")
    public ResponseEntity GetUserRatingAsync(@PathVariable Long filmId) {
        //TODO token
        //var token = Request.Headers["Authorization"].ToString().Split()[Constants.TOKEN_VALUE_INDEX];
        //var userId = _tokenDecoder.getUserIdFromToken(token);
        Long userId = Long.MAX_VALUE;
        ServiceResponse<RatingResponse> response = ratingService.GetUserRating(userId, filmId);

        if (!response.IsSucceeded()) {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

    @PostMapping(value = "/rateFilm")
    public ResponseEntity RateFilmAsync(@RequestBody SetRatingRequest setRatingRequest)
    {
        //TODO token
        //var token = Request.Headers["Authorization"].ToString().Split()[Constants.TOKEN_VALUE_INDEX];
        //var userId = _tokenDecoder.getUserIdFromToken(token);
        Long userId = Long.MAX_VALUE;

        ServiceResponse<Double> response =
                ratingService.SetRating(setRatingRequest.getRate(), setRatingRequest.getFilmId(), userId);

        if (!response.IsSucceeded()){
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

}
