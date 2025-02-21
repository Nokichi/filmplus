package ru.jabka.filmplus.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Movie(
        Long id,
        String title,
        String description,
        LocalDate releaseDate,
        Integer duration,
        Genre genre) {
}