package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.repository.mapper.LikeMapper;

@Repository
@RequiredArgsConstructor
public class LikeRepository {
    private static final String INSERT = """
            INSERT INTO filmplus.like (user_id, movie_id)
            VALUES (:user_id, :movie_id)
            RETURNING *;
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LikeMapper likeMapper;

    public Like insert(final Like like) {
        return jdbcTemplate.queryForObject(INSERT, likeToSql(like), likeMapper);
    }

    private MapSqlParameterSource likeToSql(final Like like) {
        return new MapSqlParameterSource()
                .addValue("user_id", like.userId())
                .addValue("movie_id", like.movieId());
    }
}