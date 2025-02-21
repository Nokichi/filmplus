package ru.jabka.filmplus.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Review(
        Long id,
        Long userId,
        Long movieId,
        String reviewText,
        LocalDateTime createdAt) {
}