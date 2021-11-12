package by.karelin.persistence.repositories.interfaces;

import by.karelin.domain.models.Comment;
import by.karelin.domain.pojo.CommentViewModel;

import java.util.List;

public interface ICommentRepository {
    Long createComment(Comment comment);
    Long deleteComment(Long id, Long userId);
    List<CommentViewModel> getAllByFilmId(Long filmId);
}
