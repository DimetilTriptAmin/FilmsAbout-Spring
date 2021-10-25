package by.karelin.business.services.interfaces;

import by.karelin.business.dto.Responses.CommentResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import java.util.List;

public interface ICommentService {
    ServiceResponse<CommentResponse> CreateComment(Long userId, Long filmId, String text);
    ServiceResponse<Long> DeleteComment(Long id);
    ServiceResponse<List<CommentResponse>> GetAllByFilmId(Long id);
}
