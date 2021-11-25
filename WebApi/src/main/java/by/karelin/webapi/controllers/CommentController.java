package by.karelin.webapi.controllers;

import by.karelin.business.dto.Requests.CreateCommentRequest;
import by.karelin.business.dto.Requests.UpdateCommentRequest;
import by.karelin.business.dto.Responses.CommentResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.ICommentService;
import by.karelin.business.utils.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "api/Comment")
public class CommentController {
    private final ICommentService commentService;
    private final JwtProvider jwtProvider;

    public CommentController(ICommentService commentService, JwtProvider jwtProvider){
        this.commentService = commentService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping(value = "/{filmId}/{pageNumber}/{pageSize}")
    public ResponseEntity getCommentPage(
            @PathVariable Long filmId,
            @PathVariable Integer pageNumber,
            @PathVariable Integer pageSize
    )
    {
        ServiceResponse<List<CommentResponse>> response =
                commentService.getCommentPage(filmId, pageNumber, pageSize);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<CommentResponse>>(response.getValue(), HttpStatus.OK);
    }

    @GetMapping(value = "/{filmId}/{pageNumber}/{pageSize}/deleted")
    public ResponseEntity getDeletedCommentPage(
            @RequestHeader("Authorization") String rawToken,
            @PathVariable Long filmId,
            @PathVariable Integer pageNumber,
            @PathVariable Integer pageSize
    )
    {

        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized = !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<List<CommentResponse>> response =
                commentService.getDeletedCommentPage(userId, filmId, pageNumber, pageSize);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), response.getHttpStatus());
        }

        return new ResponseEntity<List<CommentResponse>>(response.getValue(), HttpStatus.OK);
    }

    @GetMapping(value = "/{filmId}/{pageSize}")
    public ResponseEntity getPagesAmount(
            @PathVariable Long filmId,
            @PathVariable Integer pageSize
    )
    {
        ServiceResponse<Integer> response =
                commentService.getCommentPagesAmount(filmId, pageSize);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Integer>(response.getValue(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createComment(
            @RequestHeader("Authorization") String rawToken,
            @Valid @RequestBody CreateCommentRequest request
    )
    {
        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized =  !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<CommentResponse> response =
                commentService.createComment(userId, request.getFilmId(), request.getText());

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommentResponse>(response.getValue(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateComment(
            @RequestHeader("Authorization") String rawToken,
            @Valid @RequestBody UpdateCommentRequest request
    )
    {
        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized =  !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<Long> response =
                commentService.updateComment(userId, request);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response.getValue(), HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity deleteComment(
            @RequestHeader("Authorization") String rawToken,
            @PathVariable Long id
    )
    {
        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized =  !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<Long> response = commentService.deleteComment(id, userId);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(response.getValue(), HttpStatus.OK);
    }

}
