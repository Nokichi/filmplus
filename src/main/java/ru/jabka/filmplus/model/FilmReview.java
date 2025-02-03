package ru.jabka.filmplus.model;

public class FilmReview {
    private Long filmId;
    private ReviewEntity reviewEntity;

    public FilmReview(Long filmId, ReviewEntity reviewEntity) {
        this.filmId = filmId;
        this.reviewEntity = reviewEntity;
    }

    public Long getFilmId() {
        return this.filmId;
    }

    public ReviewEntity getReviewEntity() {
        return this.reviewEntity;
    }
}