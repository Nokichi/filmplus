package ru.jabka.filmplus.model;

import lombok.Builder;

@Builder
public record Like(
        Long userId,
        Long movieId) {
}