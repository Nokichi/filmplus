package ru.jabka.filmplus.repository;

import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;

import java.util.Set;

@Repository
public class FilmRepository {
    private Long indexCounter;
    private final Set<Film> films;

    public FilmRepository(Set<Film> films) {
        this.indexCounter = 0L;
        this.films = films;
    }

    public Film getById(final Long id) {
        return this.films.stream()
                .filter(film -> film.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format("Фильм по ID=%d не найден!", id)));
    }

    public Set<Film> getFilms() {
        return this.films;
    }

    public Long getNextIndex() {
        return ++this.indexCounter;
    }
}