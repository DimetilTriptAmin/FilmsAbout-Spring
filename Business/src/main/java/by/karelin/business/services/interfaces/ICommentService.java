package by.karelin.business.services.interfaces;

import by.karelin.persistence.dto.Responses.CommentResponse;
import by.karelin.persistence.dto.Responses.ServiceResponse;
import java.util.List;

public interface ICommentService {
    ServiceResponse<CommentResponse> createComment(Long userId, Long filmId, String text);
    ServiceResponse<Long> deleteComment(Long id);
    ServiceResponse<List<CommentResponse>> getAllByFilmId(Long id);
}
