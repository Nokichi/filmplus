package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.mapper.UserMapper;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private static final String INSERT = """
            INSERT INTO filmplus.user (name, email, login, birthday)
            VALUES (:name, :email, :login, :birthday)
            RETURNING *
            """;

    private static final String UPDATE = """
            UPDATE filmplus.user
            SET name = :name, email = :email, login = :login, birthday = :birthday
            WHERE id = :id
            RETURNING *
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM filmplus.user
            WHERE id = :id
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public User insert(final User user) {
        return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
    }

    public User update(final User user) {
        return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
    }

    public User getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapper);
        } catch (Throwable e) {
            throw new BadRequestException(String.format("Пользователь с id %d не найден", id));
        }
    }

    private MapSqlParameterSource userToSql(final User user) {
        return new MapSqlParameterSource()
                .addValue("id", user.id())
                .addValue("name", user.name())
                .addValue("login", user.login())
                .addValue("email", user.email())
                .addValue("birthday", user.birthDay());
    }
}