package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.GenreEnum;
import ru.jabka.filmplus.repository.FilmRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film create(final Film film) {
        validate(film);
        film.setId(filmRepository.getNextIndex());
        filmRepository.getFilms().add(film);
        return film;
    }

    public Film update(final Film film) {
        validate(film);
        Film existsFilm = filmRepository.getById(film.getId());
        existsFilm.setName(film.getName());
        existsFilm.setDescription(film.getDescription());
        existsFilm.setReleaseDate(film.getReleaseDate());
        existsFilm.setDurationInSeconds(film.getDurationInSeconds());
        existsFilm.setGenres(film.getGenres());
        return existsFilm;
    }

    public Film getById(final Long id) {
        return filmRepository.getById(id);
    }

    public void delete(final Long id) {
        filmRepository.getFilms().remove(filmRepository.getById(id));
    }

    public Set<Film> search(String name, String description, Set<GenreEnum> genres, LocalDate releaseDate) {
        Set<Film> searchResult = new HashSet<>();
        Set<Film> films = filmRepository.getFilms();
        ofNullable(name).ifPresent(n -> searchResult.addAll(films.stream()
                .filter(film -> film.getName().contains(n))
                .toList()));
        ofNullable(description).ifPresent(d -> searchResult.addAll(films.stream()
                .filter(film -> film.getDescription().contains(d))
                .toList()));
        if (!CollectionUtils.isEmpty(genres)) {
            searchResult.addAll(films.stream()
                    .filter(film -> new HashSet<>(film.getGenres()).containsAll(genres))
                    .toList());
        }
        ofNullable(releaseDate).ifPresent(rd -> searchResult.addAll(films.stream()
                .filter(film -> film.getReleaseDate().isEqual(rd))
                .toList()));
        return searchResult;
    }

    private void validate(final Film film) {
        ofNullable(film).orElseThrow(() -> new BadRequestException("Введите информацию о фильме"));
        if (!StringUtils.hasText(film.getName())) {
            throw new BadRequestException("Укажите название фильма");
        }
        if (!StringUtils.hasText(film.getDescription())) {
            throw new BadRequestException("Укажите описание фильма");
        }
        ofNullable(film.getReleaseDate()).orElseThrow(() -> new BadRequestException("Укажите дату выхода фильма"));
        ofNullable(film.getDurationInSeconds()).orElseThrow(() -> new BadRequestException("Укажите продолжительность фильма"));
        if (CollectionUtils.isEmpty(film.getGenres())) {
            throw new BadRequestException("Укажите жанры, к которым относится фильм");
        }
    }
}