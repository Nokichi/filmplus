package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.repository.LikeRepository;
import ru.jabka.filmplus.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {
    @Mock
    private LikeRepository likeRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    void setLike_success() {
        final Like like = Like.builder()
                .userId(1L)
                .movieId(2L)
                .build();
        Mockito.when(likeRepository.insert(like)).thenReturn(like);
        Like result = feedbackService.setLike(like);
        Assertions.assertEquals(like, result);
        Mockito.verify(likeRepository).insert(like);
    }

    @Test
    void addReview_success() {
        final Review review = Review.builder()
                .id(1L)
                .userId(2L)
                .movieId(3L)
                .reviewText("review")
                .build();
        Mockito.when(reviewRepository.insert(review)).thenReturn(review);
        Review result = feedbackService.addReview(review);
        Assertions.assertEquals(review, result);
        Mockito.verify(reviewRepository).insert(review);
    }

    @Test
    void setLike_error_nullLike() {
        final Like like = null;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> feedbackService.setLike(like)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите информацию об установке лайка за фильм");
        Mockito.verify(likeRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void setLike_error_nullUserId() {
        final Like like = Like.builder()
                .movieId(2L)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> feedbackService.setLike(like)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите ID пользователя, который поставил лайк");
        Mockito.verify(likeRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void setLike_error_nullMovieId() {
        final Like like = Like.builder()
                .userId(1L)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> feedbackService.setLike(like)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите ID фильма, которому поставили лайк");
        Mockito.verify(likeRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void addReview_error_nullReview() {
        final Review review = null;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> feedbackService.addReview(review)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите информацию об отзыве фильма");
        Mockito.verify(likeRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void addReview_error_nullMovieId() {
        final Review review = Review.builder()
                .id(1L)
                .userId(2L)
                .reviewText("review")
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> feedbackService.addReview(review)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите ID фильма, для которого оставлен отзыв");
        Mockito.verify(likeRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void addReview_error_nullUserId() {
        final Review review = Review.builder()
                .id(1L)
                .movieId(2L)
                .reviewText("review")
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> feedbackService.addReview(review)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите ID пользователя, который оставил отзыв");
        Mockito.verify(likeRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void addReview_error_nullReviewText() {
        final Review review = Review.builder()
                .id(1L)
                .userId(2L)
                .movieId(3L)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> feedbackService.addReview(review)
        );
        Assertions.assertEquals(exception.getMessage(), "Текст отзыва о фильме не заполнен");
        Mockito.verify(likeRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void addReview_error_emptyReviewText() {
        final Review review = Review.builder()
                .id(1L)
                .userId(2L)
                .movieId(3L)
                .reviewText("")
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> feedbackService.addReview(review)
        );
        Assertions.assertEquals(exception.getMessage(), "Текст отзыва о фильме не заполнен");
        Mockito.verify(likeRepository, Mockito.never()).insert(Mockito.any());
    }
}