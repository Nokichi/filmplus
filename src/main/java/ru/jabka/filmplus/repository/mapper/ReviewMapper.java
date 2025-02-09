package ru.jabka.filmplus.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabka.filmplus.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class ReviewMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Review.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .movieId(rs.getLong("movie_id"))
                .reviewText(rs.getString("review_text"))
                .createdAt(rs.getObject("created_at", LocalDateTime.class))
                .build();
    }
}
