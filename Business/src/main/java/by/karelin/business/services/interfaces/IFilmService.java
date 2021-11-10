package by.karelin.business.services.interfaces;

import by.karelin.persistence.dto.Responses.ServiceResponse;
import by.karelin.domain.models.Film;

import java.util.List;

public interface IFilmService {
    List<Film> FindAll();
    ServiceResponse<Film> GetById(Long id);
    ServiceResponse<Long> GetIdByTitle(String title);
}
