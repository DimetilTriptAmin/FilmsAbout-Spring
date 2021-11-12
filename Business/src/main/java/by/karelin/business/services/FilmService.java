package by.karelin.business.services;

import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IFilmService;
import by.karelin.domain.models.Film;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmService implements IFilmService {
    private IFilmRepository filmRepository;

    public FilmService(IFilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    public List<Film> FindAll() {
        return filmRepository.getAll();
    }

    public ServiceResponse<Film> GetById(Long id) {

        Film film = filmRepository.getById(id);

        if (film == null) {
            return new ServiceResponse<Film>("Film not found");
        }

        return new ServiceResponse<Film>(film);
    }

    public ServiceResponse<Long> GetIdByTitle(String title) {
        Long film = filmRepository.getIdByTitle(title);

        if (film == null) {
            return new ServiceResponse<Long>("Film not found");
        }

        return new ServiceResponse<Long>(film);
    }

}
