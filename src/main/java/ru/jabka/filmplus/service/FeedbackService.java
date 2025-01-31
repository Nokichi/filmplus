package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.FilmReview;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.model.ReviewEntity;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.LikeRepository;
import ru.jabka.filmplus.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Service
public class FeedbackService {
    private final UserService userService;
    private final FilmService filmService;
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    public FeedbackService(UserService userService, FilmService filmService, LikeRepository likeRepository, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.filmService = filmService;
        this.likeRepository = likeRepository;
        this.reviewRepository = reviewRepository;
    }

    public void setLike(final Like like) {
        validateLike(like);
        filmService.getById(like.getFilmId());
        Set<User> likeAuthors = ofNullable(likeRepository.getByFilmId(like.getFilmId()))
                .orElse(new HashSet<>());
        likeAuthors.add(userService.getById(like.getUserId()));
        likeRepository.getLikes().put(like.getFilmId(), likeAuthors);
    }

    public FilmReview addReview(final FilmReview filmReview) {
        validateReview(filmReview);
        filmService.getById(filmReview.getFilmId());
        userService.getById(filmReview.getReviewEntity().getUserId());
        filmReview.getReviewEntity().setMoment(LocalDateTime.now());
        List<ReviewEntity> reviewList = ofNullable(reviewRepository.getReviews().get(filmReview.getFilmId()))
                .orElse(new ArrayList<>());
        reviewList.add(filmReview.getReviewEntity());
        reviewRepository.getReviews().put(filmReview.getFilmId(), reviewList);
        return filmReview;
    }

    private void validateLike(final Like like) {
        ofNullable(like.getUserId()).orElseThrow(() -> new BadRequestException("Укажите ID пользователя, который поставил лайк"));
        ofNullable(like.getFilmId()).orElseThrow(() -> new BadRequestException("Укажите ID фильма, которому поставили лайк"));
    }

    private void validateReview(final FilmReview review) {
        ofNullable(review.getFilmId()).orElseThrow(() -> new BadRequestException("Укажите ID фильма, для которого оставлен отзыв"));
        ofNullable(review.getReviewEntity().getUserId()).orElseThrow(() -> new BadRequestException("Укажите ID пользователя, который оставил отзыв"));
        if (!StringUtils.hasText(review.getReviewEntity().getText())) {
            throw new BadRequestException("Текст отзыва о фильме не заполнен");
        }
    }
}