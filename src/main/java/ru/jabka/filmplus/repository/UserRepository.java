package ru.jabka.filmplus.repository;

import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;

import java.util.Set;

@Repository
public class UserRepository {
    private Long indexCounter;
    private final Set<User> users;

    public UserRepository(Set<User> users) {
        this.indexCounter = 0L;
        this.users = users;
    }

    public User getById(final Long id) {
        return this.users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format("Пользователь по ID=%d не найден!", id)));
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public Long getNextIndex() {
        return ++this.indexCounter;
    }
}