package by.karelin.persistence.repositories;

import by.karelin.domain.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFilmRepository extends JpaRepository<Film, Long> {

}
