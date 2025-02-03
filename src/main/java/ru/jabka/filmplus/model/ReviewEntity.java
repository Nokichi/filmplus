package ru.jabka.filmplus.model;

import java.time.LocalDateTime;

public class ReviewEntity {
    private Long userId;
    private LocalDateTime moment;
    private String text;

    public ReviewEntity(Long userId, LocalDateTime moment, String review) {
        this.userId = userId;
        this.moment = moment;
        this.text = review;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public Long getUserId() {
        return this.userId;
    }

    public LocalDateTime getMoment() {
        return this.moment;
    }

    public String getText() {
        return this.text;
    }
}