package by.karelin.persistence.repositories;

import by.karelin.persistence.dto.Responses.RatingResponse;
import by.karelin.persistence.repositories.interfaces.IRatingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Optional;

@Repository
public class RatingRepository implements IRatingRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public RatingResponse getUserRating(Long userId, Long filmId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_RATING_GET_FOR_USER", RatingResponse.class)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Class.class,
                        ParameterMode.REF_CURSOR);
        query.setParameter(1, filmId);
        query.setParameter(2, userId);
        query.execute();

        Optional<RatingResponse> rating = query.getResultList().stream().findFirst();
        return rating.orElse(null);
    }

    @Override
    public boolean trySetRating(int rate, Long filmId, Long userId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_USER_TRY_REGISTER")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
        query.setParameter(1, rate);
        query.setParameter(2, filmId);
        query.setParameter(3, userId);
        boolean succeeded = query.execute();

        return succeeded;
    }
}
