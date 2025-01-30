package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.FilmReview;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.model.ReviewEntity;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.FilmRepository;
import ru.jabka.filmplus.repository.LikeRepository;
import ru.jabka.filmplus.repository.ReviewRepository;
import ru.jabka.filmplus.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Optional.ofNullable;

@Service
public class FeedbackService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    public FeedbackService(UserRepository userRepository, FilmRepository filmRepository, LikeRepository likeRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
        this.likeRepository = likeRepository;
        this.reviewRepository = reviewRepository;
    }

    public void setLike(final Like like) {
        validateLike(like);
        filmRepository.getById(like.getFilmId());
        Set<User> likeAuthors = ofNullable(likeRepository.getByFilmId(like.getFilmId()))
                .orElse(new HashSet<>());
        likeAuthors.add(userRepository.getById(like.getUserId()));
        likeRepository.getLikes().put(like.getFilmId(), likeAuthors);
    }

    public Set<User> getLikesByFilmId(final Long id) {
        filmRepository.getById(id);
        return ofNullable(likeRepository.getByFilmId(id)).orElse(Collections.emptySet());
    }

    public FilmReview addReview(final FilmReview filmReview) {
        validateReview(filmReview);
        filmRepository.getById(filmReview.getFilmId());
        userRepository.getById(filmReview.getReviewEntity().getUserId());
        filmReview.getReviewEntity().setMoment(LocalDateTime.now());
        List<ReviewEntity> reviewList = ofNullable(reviewRepository.getReviews().get(filmReview.getFilmId()))
                .orElse(new ArrayList<>());
        reviewList.add(filmReview.getReviewEntity());
        reviewRepository.getReviews().put(filmReview.getFilmId(), reviewList);
        return filmReview;
    }

    public List<ReviewEntity> getReviewsByFilmId(final Long id) {
        filmRepository.getById(id);
        return ofNullable(reviewRepository.getReviews().get(id)).orElse(Collections.emptyList());
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