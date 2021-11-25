package by.karelin.persistence.repositories.interfaces;

import by.karelin.domain.models.Comment;
import by.karelin.domain.pojo.CommentViewModel;

import java.util.List;

public interface ICommentRepository {
    Long createComment(Comment comment);
    Long updateComment(Long id, Long userId, String text);
    Long deleteComment(Long id, Long userId);
    List<CommentViewModel> getCommentPage(Long filmId, Integer pageNumber, Integer pageSize);
    List<CommentViewModel> getDeletedCommentPage(Long filmId, Integer pageNumber, Integer pageSize);
    Integer getCommentPagesAmount(Long filmId, Integer pageSize);
}
