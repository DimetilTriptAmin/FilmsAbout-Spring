package by.karelin.persistence.repositories.interfaces;

import by.karelin.domain.models.Film;
import java.util.List;

public interface IFilmRepository {
    List<Film> getAll();
    Film getById(Long id);
    Long getIdByTitle(String title);
    Double updateRating(Long id);
}
