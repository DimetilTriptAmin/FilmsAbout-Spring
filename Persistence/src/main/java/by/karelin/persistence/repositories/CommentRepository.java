package by.karelin.persistence.repositories;

import by.karelin.domain.models.Comment;
import by.karelin.persistence.repositories.interfaces.ICommentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.Clob;
import java.util.Date;
import java.util.List;

@Repository
public class CommentRepository implements ICommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean tryCreateComment(Comment comment) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_comment_try_create")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN);
        query.setParameter(1, comment.getText());
        query.setParameter(2, comment.getPublishDate());
        query.setParameter(3, comment.isDeleted());
        query.setParameter(4, comment.getFilm().getId());
        query.setParameter(5, comment.getUser().getId());
        boolean succeeded = query.execute();

        return succeeded;
    }

    @Override
    public boolean tryDeleteComment(Comment comment) {
        return false;
    }

    @Override
    public List<Comment> getAllByFilmId(Long filmId) {
        return null;
    }
}
