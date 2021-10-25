package by.karelin.webapi.controllers;

import by.karelin.business.dto.Requests.CreateCommentRequest;
import by.karelin.business.dto.Responses.CommentResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/Comment")
public class CommentController {
    private ICommentService commentService;

    public CommentController (ICommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping(value = "/getAll{filmId}")
    public ResponseEntity GetAllByFilmIdAsync(@PathVariable Long filmId)
    {
        ServiceResponse<List<CommentResponse>> response = commentService.GetAllByFilmId(filmId);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<CommentResponse>>(response.getValue(), HttpStatus.OK);
    }

    @PostMapping(value ="/createComment")
    public ResponseEntity CreateCommentAsync(@RequestBody CreateCommentRequest request)
    {
        // TODO token
        //var token = Request.Headers["Authorization"].ToString().Split()[Constants.TOKEN_VALUE_INDEX];
        //var userId = _tokenDecoder.getUserIdFromToken(token);
        Long userId = Long.MAX_VALUE;

        ServiceResponse<CommentResponse> response =
                commentService.CreateComment(userId, request.getFilmId(), request.getText());

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommentResponse>(response.getValue(), HttpStatus.OK);
    }

    @DeleteMapping(value="/deleteComment/{id}")
    public ResponseEntity DeleteCommentAsync(@PathVariable Long id)
    {
        ServiceResponse<Long> response = commentService.DeleteComment(id);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(response.getValue(), HttpStatus.OK);
    }

}
