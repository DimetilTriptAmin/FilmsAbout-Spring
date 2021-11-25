package by.karelin.business.services.interfaces;

import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.exceptions.ServiceException;
import by.karelin.domain.models.Film;
import javassist.NotFoundException;

import java.util.List;

public interface IFilmService {
    List<Film> FindAll();
    ServiceResponse<Film> GetById(Long id) throws ServiceException, NotFoundException;
    ServiceResponse<Long> GetIdByTitle(String title) throws ServiceException, NotFoundException;
}
