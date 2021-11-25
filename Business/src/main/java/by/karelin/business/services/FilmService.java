package by.karelin.business.services;

import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.exceptions.ServiceException;
import by.karelin.business.services.interfaces.IFilmService;
import by.karelin.business.utils.enums.ErrorCode;
import by.karelin.domain.models.Film;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmService implements IFilmService {
    private final IFilmRepository filmRepository;
    private static Logger log = Logger.getLogger(FilmService.class.getName());

    public FilmService(IFilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> FindAll() {
        return filmRepository.getAll();
    }

    public ServiceResponse<Film> GetById(Long id)
            throws ServiceException, NotFoundException
    {
        try {
            Film film = filmRepository.getById(id);

            if (film == null) {
                throw new NotFoundException("Film not found");
            }

            return new ServiceResponse<>(film, HttpStatus.OK);
        } catch (NotFoundException ex) {
            log.error(ex.getMessage());
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ServiceException("Server is offline.");
        }
    }

    public ServiceResponse<Long> GetIdByTitle(String title)
            throws ServiceException, NotFoundException {
        try {
            Long filmId = filmRepository.getIdByTitle(title);

            if (filmId == ErrorCode.RepositoryTransactionError.getValue()) {
                throw new NotFoundException("Film not found");
            }

            return new ServiceResponse<Long>(filmId, HttpStatus.OK);
        } catch (NotFoundException ex) {
            log.error(ex.getMessage());
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ServiceException("Server is offline.");
        }

    }
}
