package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.service.MovieService;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Фильмы")
@RequiredArgsConstructor
@RequestMapping("/api/v1/film")
public class FilmController {
    private final MovieService movieService;

    @Operation(summary = "Добавить новый фильм")
    @PostMapping
    public Movie create(@RequestBody final Movie movie) {
        return movieService.create(movie);
    }

    @Operation(summary = "Обновить данные фильма")
    @PatchMapping
    public Movie update(@RequestBody final Movie movie) {
        return movieService.update(movie);
    }

    @Operation(summary = "Получить фильм по ID")
    @GetMapping("/{id}")
    public Movie get(@PathVariable final Long id) {
        return movieService.getById(id);
    }

    @Operation(summary = "Поиск фильма по параметрам")
    @GetMapping
    public List<Movie> search(@RequestParam(required = false) String title,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) Genre genre,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate releaseDate) {
        return movieService.search(title, description, genre, releaseDate);
    }
}