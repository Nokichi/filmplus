package ru.jabka.filmplus.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record User(
        Long id,
        String name,
        String email,
        String login,
        LocalDate birthDay) {
}