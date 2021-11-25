package by.karelin.persistence.repositories;

import by.karelin.domain.models.Film;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository implements IFilmRepository {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public List<Film> getAll() {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("SP_FILM_GET_ALL", Film.class)
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();

        List<Film> films = query.getResultList();
        return films;
    }

    @Override
    public Film getById(Long id) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_film_get_by_id", Film.class)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR);
        query.setParameter(1, id);
        query.execute();

        Optional<Film> film = query.getResultList().stream().findFirst();
        return film.orElse(null);
    }

    @Override
    public Long getIdByTitle(String title) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_film_get_id_by_title")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.OUT);
        query.setParameter(1, title);
        query.execute();

        Long id = (Long) query.getOutputParameterValue(2);
        return id;
    }
}
