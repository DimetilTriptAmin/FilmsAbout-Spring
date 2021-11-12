package by.karelin.business.services.interfaces;

import by.karelin.business.dto.Responses.CommentResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import java.util.List;

public interface ICommentService {
    ServiceResponse<CommentResponse> createComment(Long userId, Long filmId, String text);
    ServiceResponse<Long> deleteComment(Long id, Long userId);
    ServiceResponse<List<CommentResponse>> getAllByFilmId(Long id);
}
