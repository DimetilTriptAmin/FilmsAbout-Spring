package by.karelin.persistence.repositories;

import by.karelin.domain.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {
}
