package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.jabka.filmplus.model.FilmReview;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.model.ReviewEntity;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.service.FeedbackService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/feedback")
@Tag(name = "Обратная связь")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Operation(summary = "Поставить лайк фильму")
    @PostMapping("/like")
    public void setLike(@RequestBody final Like like) {
        feedbackService.setLike(like);
    }

    @Operation(summary = "Получить список лайков фильма")
    @GetMapping("/like/{filmId}")
    public Set<User> getLikes(@PathVariable final Long filmId) {
        return feedbackService.getLikesByFilmId(filmId);
    }

    @Operation(summary = "Оставить отзыв фильму")
    @PostMapping("/review")
    public FilmReview addReview(@RequestBody final FilmReview review) {
        return feedbackService.addReview(review);
    }

    @Operation(summary = "Получить список отзывов фильма")
    @GetMapping("/reviews/{filmId}")
    public List<ReviewEntity> getReviewByFilmId(@PathVariable final Long filmId) {
        return feedbackService.getReviewsByFilmId(filmId);
    }
}