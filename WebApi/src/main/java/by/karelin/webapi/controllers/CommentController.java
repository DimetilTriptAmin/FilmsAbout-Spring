package by.karelin.webapi.controllers;

import by.karelin.persistence.dto.Requests.CreateCommentRequest;
import by.karelin.persistence.dto.Responses.CommentResponse;
import by.karelin.persistence.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.ICommentService;
import by.karelin.business.utils.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/Comment")
public class CommentController {
    private final ICommentService commentService;
    private final JwtProvider jwtProvider;

    public CommentController(ICommentService commentService, JwtProvider jwtProvider){
        this.commentService = commentService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping(value = "/all/{filmId}")
    public ResponseEntity GetAllByFilmIdAsync(@PathVariable Long filmId)
    {
        ServiceResponse<List<CommentResponse>> response = commentService.getAllByFilmId(filmId);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<CommentResponse>>(response.getValue(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity CreateCommentAsync(
            @RequestHeader("Authorization") String rawToken,
            @RequestBody CreateCommentRequest request
    )
    {
        String token = rawToken.substring("Bearer ".length());
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<CommentResponse> response =
                commentService.createComment(userId, request.getFilmId(), request.getText());

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommentResponse>(response.getValue(), HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity DeleteCommentAsync(@PathVariable Long id)
    {
        ServiceResponse<Long> response = commentService.deleteComment(id);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(response.getValue(), HttpStatus.OK);
    }

}
