package services;

import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.exceptions.ServiceException;
import by.karelin.business.services.FilmService;
import by.karelin.domain.models.Film;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = by.karelin.filmsabout.FilmsAboutApplication.class)
public class FilmServiceTest {
    @Autowired
    private FilmService filmService;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(filmService);
    }

    @Test
    public void findAll() throws ServiceException, NotFoundException {
        assertTimeout(Duration.ofMinutes(3), () -> {
            filmService.FindAll();
        });
    }

    @ParameterizedTest()
    @ValueSource(longs = {6,7,8})
    public void getById(Long id) throws ServiceException, NotFoundException {
        ServiceResponse<Film> film = filmService.GetById(id);
        assertNotNull(film.getValue());
    }

    @ParameterizedTest()
    @ValueSource(longs = {-1,-2,-3})
    public void getByIdNotFoundException(Long id) throws ServiceException, NotFoundException {
        assertThrows(NotFoundException.class, () ->
                filmService.GetById(id));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Scarface"})
    public void getIdByTitle(String title) throws ServiceException, NotFoundException {
        ServiceResponse<Long> id = filmService.GetIdByTitle(title);
        assertNotNull(id.getValue());
    }

    @ParameterizedTest()
    @ValueSource(strings = {"not-existing-title-i-swear", "not-existing-title-i-swear2", ""})
    public void getIdByTitleNotFoundException(String title) throws ServiceException, NotFoundException {
        assertThrows(NotFoundException.class, () ->
                filmService.GetIdByTitle(title));
    }
}
