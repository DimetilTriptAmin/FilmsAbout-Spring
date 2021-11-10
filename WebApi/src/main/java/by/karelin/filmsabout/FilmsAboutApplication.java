package by.karelin.filmsabout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"by.karelin.domain.models"})
@EnableJpaRepositories(basePackages = {"by.karelin.persistence.repositories"})
@ComponentScan(basePackages = {"by.karelin"})
public class FilmsAboutApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilmsAboutApplication.class);
    }
}
