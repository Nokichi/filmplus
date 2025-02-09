package ru.jabka.filmplus.model;

public record ApiError(
        Boolean success,
        String message) {
}