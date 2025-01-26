package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.GenreEnum;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@Tag(name = "Фильмы")
public class FilmController {

    @PostMapping
    public Film create(@RequestBody final Film film) {
        return null;
    }

    @PatchMapping
    public Film update(@RequestBody final Film film) {
        return null;
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
    }

    @GetMapping
    public List<Film> search(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String description,
                             @RequestParam(required = false) GenreEnum genre,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate) {
        return null;
    }
}
