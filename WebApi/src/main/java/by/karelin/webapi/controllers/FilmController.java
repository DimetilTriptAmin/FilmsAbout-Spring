package by.karelin.webapi.controllers;

import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.IFilmService;
import by.karelin.domain.models.Film;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/Film")
public class FilmController {
    private IFilmService filmService;

    public FilmController (IFilmService filmService){
        this.filmService = filmService;
    }

    @GetMapping(value = "/all")
    public @ResponseBody ResponseEntity GetFilms()
    {
        return new ResponseEntity<List<Film>>(filmService.FindAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity GetFilm(@PathVariable Long id)
    {
        ServiceResponse<Film> response = filmService.GetById(id);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<String>(response.getErrorMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Film>(response.getValue(), HttpStatus.OK);
    }

    @GetMapping(value = "getId/{title}")
    public @ResponseBody ResponseEntity GetFilmIdAsync(@PathVariable String title)
    {
        ServiceResponse<Long> response = filmService.GetIdByTitle(title);

        if (!response.IsSucceeded()) {
            return new ResponseEntity<String>(response.getErrorMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Long>(response.getValue(), HttpStatus.OK);
    }
}
