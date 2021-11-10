package by.karelin.persistence.repositories.interfaces;

import by.karelin.domain.models.Comment;

import java.util.List;

public interface ICommentRepository {
    boolean tryCreateComment(Comment comment);
    boolean tryDeleteComment(Comment comment);
    List<Comment> getAllByFilmId(Long filmId);
}
