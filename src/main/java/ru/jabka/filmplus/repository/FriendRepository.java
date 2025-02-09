package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.model.FriendRequest;

@Repository
@RequiredArgsConstructor
public class FriendRepository {
    private static final String INSERT = """
            INSERT INTO filmplus.friend (user_id, friend_id)
            VALUES 
                (:user_id, :friend_id), 
                (:friend_id, :user_id);
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void insert(final FriendRequest friendRequest) {
        jdbcTemplate.update(INSERT, friendToSql(friendRequest));
    }

    private MapSqlParameterSource friendToSql(final FriendRequest friendRequest) {
        return new MapSqlParameterSource()
                .addValue("user_id", friendRequest.userId())
                .addValue("friend_id", friendRequest.friendId());
    }
}