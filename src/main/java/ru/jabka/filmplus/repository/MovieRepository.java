package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.repository.mapper.MovieMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private static final String INSERT = """
            INSERT INTO filmplus.movie (title, description, release_date, duration, genre)
            VALUES (:title, :description, :release_date, :duration, :genre)
            RETURNING *
            """;

    private static final String UPDATE = """
            UPDATE filmplus.movie
            SET title = :title, description = :description, release_date = :release_date, duration = :duration, genre = :genre
            WHERE id = :id
            RETURNING *
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM filmplus.movie
            WHERE id = :id
            """;

    private static final String SEARCH = """
            SELECT *
            FROM filmplus.movie
            WHERE title LIKE :title
            AND description LIKE :description
            AND genre LIKE :genre
            AND (:release_date = '' OR release_date = TO_DATE(:release_date, 'YYYY-MM-DD'))
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final MovieMapper movieMapper;

    public Movie insert(Movie movie) {
        return jdbcTemplate.queryForObject(INSERT, movieToSql(movie), movieMapper);
    }

    public Movie update(Movie movie) {
        return jdbcTemplate.queryForObject(UPDATE, movieToSql(movie), movieMapper);
    }

    public List<Movie> search(String title, String description, String genre, String releaseDate) {
        return jdbcTemplate.query(SEARCH, new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("description", description)
                .addValue("genre", genre)
                .addValue("release_date", releaseDate), movieMapper);
    }

    public Movie getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), movieMapper);
        } catch (Throwable e) {
            throw new BadRequestException(String.format("Фильм с id %d не найден", id));
        }
    }

    private MapSqlParameterSource movieToSql(Movie movie) {
        return new MapSqlParameterSource()
                .addValue("title", movie.title())
                .addValue("description", movie.description())
                .addValue("release_date", movie.releaseDate())
                .addValue("duration", movie.duration())
                .addValue("genre", movie.genre().name());
    }
}