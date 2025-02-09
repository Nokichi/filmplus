package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.repository.LikeRepository;
import ru.jabka.filmplus.repository.ReviewRepository;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(rollbackFor = Throwable.class)
    public Like setLike(final Like like) {
        validateLike(like);
        return likeRepository.insert(like);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Review addReview(final Review review) {
        validateReview(review);
        return reviewRepository.insert(review);
    }

    private void validateLike(final Like like) {
        ofNullable(like.userId()).orElseThrow(() -> new BadRequestException("Укажите ID пользователя, который поставил лайк"));
        ofNullable(like.movieId()).orElseThrow(() -> new BadRequestException("Укажите ID фильма, которому поставили лайк"));
    }

    private void validateReview(final Review review) {
        ofNullable(review.movieId()).orElseThrow(() -> new BadRequestException("Укажите ID фильма, для которого оставлен отзыв"));
        ofNullable(review.userId()).orElseThrow(() -> new BadRequestException("Укажите ID пользователя, который оставил отзыв"));
        if (!StringUtils.hasText(review.reviewText())) {
            throw new BadRequestException("Текст отзыва о фильме не заполнен");
        }
    }
}