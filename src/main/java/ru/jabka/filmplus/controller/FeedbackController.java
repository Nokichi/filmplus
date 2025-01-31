package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.FilmReview;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.service.FeedbackService;

@RestController
@Tag(name = "Обратная связь")
@RequestMapping("/api/v1/feedback")
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

    @Operation(summary = "Оставить отзыв фильму")
    @PostMapping("/review")
    public FilmReview addReview(@RequestBody final FilmReview review) {
        return feedbackService.addReview(review);
    }
}