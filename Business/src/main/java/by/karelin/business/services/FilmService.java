package by.karelin.business.services;

import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IFilmService;
import by.karelin.domain.models.Film;
import by.karelin.persistence.repositories.IFilmRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FilmService implements IFilmService {
    private IFilmRepository filmRepository;

    public FilmService(IFilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    public List<Film> FindAll() {
        return filmRepository.findAll();
    }

    public ServiceResponse<Film> GetById(Long id) {

        if (filmRepository.existsById(id)) {
            Film film = filmRepository.getById(id);
            return new ServiceResponse<Film>(film);
        }

        return new ServiceResponse<Film>("Film not found");
    }

    public ServiceResponse<Long> GetIdByTitle(String title) {
        Film film = new Film();
        film.setTitle(title);

        Example<Film> filmExample = Example.of(film);

        Optional<Film> filmOptional = filmRepository.findAll(filmExample).stream().findFirst();

        if(filmOptional.isEmpty()) {
            return new ServiceResponse<>("Film not found");
        }

        return new ServiceResponse<>(filmOptional.get().getId());
    }
}
