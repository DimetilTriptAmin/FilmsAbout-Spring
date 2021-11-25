package by.karelin.persistence.repositories;

import by.karelin.domain.models.User;
import by.karelin.persistence.repositories.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.Clob;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> getAllEmails() {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_USER_GET_EMAILS")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);
        query.execute();

        List<String> emails = query.getResultList();
        return emails;
    }

    @Override
    public User getById(Long id) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_USER_GET_BY_ID", User.class)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR);
        query.setParameter(1, id);
        query.execute();

        Optional<User> user = query.getResultList().stream().findFirst();
        return user.orElse(null);
    }

    @Override
    public User getByUsername(String userName) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_USER_GET_BY_USERNAME", User.class)
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR);
        query.setParameter(1, userName);
        query.execute();

        Optional<User> user = query.getResultList().stream().findFirst();
        return user.orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_USER_GET_BY_EMAIL", User.class)
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR);
        query.setParameter(1, email);
        query.execute();

        Optional<User> user = query.getResultList().stream().findFirst();
        return user.orElse(null);
    }

    @Override
    public Long registerUser(User user) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_USER_REGISTER")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Clob.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class, ParameterMode.OUT);
        query.setParameter(1, user.getName());
        query.setParameter(2, user.getPasswordHash());
        query.setParameter(3, org.hibernate.engine.jdbc.NonContextualLobCreator.INSTANCE.createClob(user.getAvatar()));
        query.setParameter(4, user.getEmail());

        query.execute();

        return (Long) query.getOutputParameterValue(5);
    }

    @Override
    public Long updateAvatar(Long id, String avatar) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_USER_UPDATE_AVATAR")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Clob.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.OUT);
        query.setParameter(1, id);
        query.setParameter(2, org.hibernate.engine.jdbc.NonContextualLobCreator.INSTANCE.createClob(avatar));
        query.execute();

        return (Long) query.getOutputParameterValue(3);
    }

    @Override
    public Long changePassword(Long id, String passwordHash) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_USER_UPDATE_PASSWORD")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.OUT);
        query.setParameter(1, id);
        query.setParameter(2, passwordHash);
        query.execute();

        return (Long) query.getOutputParameterValue(3);
    }
}
