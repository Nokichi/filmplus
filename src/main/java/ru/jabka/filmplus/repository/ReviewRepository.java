package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.repository.mapper.ReviewMapper;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private static final String INSERT = """
            INSERT INTO filmplus.review (user_id, movie_id, review_text)
            VALUES (:user_id, :movie_id, :review_text)
            RETURNING *
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ReviewMapper reviewMapper;

    public Review insert(final Review review) {
        return jdbcTemplate.queryForObject(INSERT, reviewToSql(review), reviewMapper);
    }

    private MapSqlParameterSource reviewToSql(final Review review) {
        return new MapSqlParameterSource()
                .addValue("user_id", review.userId())
                .addValue("movie_id", review.movieId())
                .addValue("review_text", review.reviewText());
    }
}