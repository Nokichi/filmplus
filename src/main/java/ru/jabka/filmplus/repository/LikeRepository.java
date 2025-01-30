package ru.jabka.filmplus.repository;

import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.model.User;

import java.util.Map;
import java.util.Set;

@Repository
public class LikeRepository {
    private final Map<Long, Set<User>> likes;

    public LikeRepository(Map<Long, Set<User>> likes) {
        this.likes = likes;
    }

    public Set<User> getByFilmId(Long id) {
        return this.likes.get(id);
    }

    public Map<Long, Set<User>> getLikes() {
        return this.likes;
    }
}