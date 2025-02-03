package ru.jabka.filmplus.repository;

import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.model.ReviewEntity;

import java.util.List;
import java.util.Map;

@Repository
public class ReviewRepository {
    private final Map<Long, List<ReviewEntity>> reviews;

    public ReviewRepository(Map<Long, List<ReviewEntity>> reviews) {
        this.reviews = reviews;
    }

    public Map<Long, List<ReviewEntity>> getReviews() {
        return this.reviews;
    }
}