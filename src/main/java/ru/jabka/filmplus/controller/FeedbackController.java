package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.service.FeedbackService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Обратная связь")
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "Поставить лайк фильму")
    @PostMapping("/like")
    public Like setLike(@RequestBody final Like like) {
        return feedbackService.setLike(like);
    }

    @Operation(summary = "Оставить отзыв фильму")
    @PostMapping("/review")
    public Review addReview(@RequestBody final Review review) {
        return feedbackService.addReview(review);
    }
}