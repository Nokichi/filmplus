package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.repository.MovieRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    @Transactional(rollbackFor = Throwable.class)
    public Movie create(final Movie movie) {
        validate(movie);
        return movieRepository.insert(movie);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Movie update(final Movie movie) {
        validate(movie);
        return movieRepository.update(movie);
    }

    @Transactional(readOnly = true)
    public Movie getById(final Long id) {
        return movieRepository.getById(id);
    }

    @Transactional(readOnly = true)
    public List<Movie> search(String title, String description, Genre genre, LocalDate releaseDate) {
        String titleWrapper = "%" + ofNullable(title).orElse("") + "%";
        String descriptionWrapper = "%" + ofNullable(description).orElse("") + "%";
        String genreWrapper = "";
        if (ofNullable(genre).isPresent()) {
            genreWrapper = genre.name();
        }
        genreWrapper = "%" + genreWrapper + "%";
        String releaseDateWrapper = "";
        if (ofNullable(releaseDate).isPresent()) {
            releaseDateWrapper = releaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return movieRepository.search(
                titleWrapper,
                descriptionWrapper,
                genreWrapper,
                releaseDateWrapper
        );
    }

    private void validate(final Movie movie) {
        ofNullable(movie).orElseThrow(() -> new BadRequestException("Введите информацию о фильме"));
        if (!StringUtils.hasText(movie.title())) {
            throw new BadRequestException("Укажите название фильма");
        }
        if (!StringUtils.hasText(movie.description())) {
            throw new BadRequestException("Укажите описание фильма");
        }
        ofNullable(movie.releaseDate()).orElseThrow(() -> new BadRequestException("Укажите дату выхода фильма"));
        ofNullable(movie.duration()).orElseThrow(() -> new BadRequestException("Укажите продолжительность фильма"));
        ofNullable(movie.genre()).orElseThrow(() -> new BadRequestException("Укажите жанр, к которому относится фильм"));
    }
}