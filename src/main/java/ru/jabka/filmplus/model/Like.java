package ru.jabka.filmplus.model;

public class Like {
    private Long userId;

    private Long filmId;

    public Like(Long userId, Long filmId) {
        this.userId = userId;
        this.filmId = filmId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getFilmId() {
        return this.filmId;
    }
}