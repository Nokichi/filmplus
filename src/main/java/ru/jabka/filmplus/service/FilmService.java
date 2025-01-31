package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.repository.FilmRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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
        existsFilm.setDuration(film.getDuration());
        existsFilm.setGenres(film.getGenres());
        return existsFilm;
    }

    public Film getById(final Long id) {
        return filmRepository.getById(id);
    }

    public void delete(final Long id) {
        filmRepository.getFilms().remove(filmRepository.getById(id));
    }

    public Set<Film> search(String name, String description, Set<Genre> genres, LocalDate releaseDate) {
        Set<Film> searchResult = new HashSet<>();
        if (Stream.of(name, description, genres, releaseDate).allMatch(Objects::isNull)) {
            return searchResult;
        }
        Set<Film> films = filmRepository.getFilms();
        for (Film film : films) {
            if (ofNullable(name).isPresent() && film.getName().contains(name)) {
                searchResult.add(film);
                continue;
            }
            if (ofNullable(description).isPresent() && film.getDescription().contains(description)) {
                searchResult.add(film);
                continue;
            }
            if (!CollectionUtils.isEmpty(genres) && film.getGenres().containsAll(genres)) {
                searchResult.add(film);
                continue;
            }
            if (ofNullable(releaseDate).isPresent() && film.getReleaseDate().isEqual(releaseDate)) {
                searchResult.add(film);
            }
        }
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
        ofNullable(film.getDuration()).orElseThrow(() -> new BadRequestException("Укажите продолжительность фильма"));
        if (CollectionUtils.isEmpty(film.getGenres())) {
            throw new BadRequestException("Укажите жанры, к которым относится фильм");
        }
    }
}