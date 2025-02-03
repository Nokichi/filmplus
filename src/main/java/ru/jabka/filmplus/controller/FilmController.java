package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.service.FilmService;

import java.time.LocalDate;
import java.util.Set;

@RestController
@Tag(name = "Фильмы")
@RequestMapping("/api/v1/film")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Operation(summary = "Добавить новый фильм")
    @PostMapping
    public Film create(@RequestBody final Film film) {
        return filmService.create(film);
    }

    @Operation(summary = "Обновить данные фильма")
    @PatchMapping
    public Film update(@RequestBody final Film film) {
        return filmService.update(film);
    }

    @Operation(summary = "Получить фильм по ID")
    @GetMapping("/{id}")
    public Film get(@PathVariable final Long id) {
        return filmService.getById(id);
    }

    @Operation(summary = "Удалить фильм по ID")
    @DeleteMapping("{id}")
    public void delete(@PathVariable final Long id) {
        filmService.delete(id);
    }

    @Operation(summary = "Поиск фильма по параметрам")
    @GetMapping
    public Set<Film> search(@RequestParam(required = false) String name,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false) Set<Genre> genres,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate releaseDate) {
        return filmService.search(name, description, genres, releaseDate);
    }
}