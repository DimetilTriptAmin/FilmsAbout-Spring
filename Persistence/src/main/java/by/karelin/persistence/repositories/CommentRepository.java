package by.karelin.persistence.repositories;

import by.karelin.domain.models.Comment;
import by.karelin.domain.pojo.CommentViewModel;
import by.karelin.persistence.repositories.interfaces.ICommentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Repository
public class CommentRepository implements ICommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long createComment(Comment comment) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_comment_create")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, Long.class, ParameterMode.OUT);
        query.setParameter(1, comment.getText());
        query.setParameter(2, comment.getPublishDate());
        query.setParameter(3, 0);
        query.setParameter(4, comment.getFilm().getId());
        query.setParameter(5, comment.getUser().getId());
        query.execute();
        Long id = (Long) query.getOutputParameterValue(6);
        return id;
    }

    @Override
    public Long deleteComment(Long id, Long userId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_comment_delete")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.OUT);
        query.setParameter(1, id);
        query.setParameter(2, userId);
        query.execute();

        Long idResult = (Long) query.getOutputParameterValue(3);
        return idResult;
    }
    @Override
    public List<CommentViewModel> getAllByFilmId(Long filmId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_COMMENT_GET_ALL", "CommentViewModelMapping")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR);
        query.setParameter(1, filmId);
        query.execute();

        List<CommentViewModel> comments = query.getResultList();

        return comments;
    }
}
