package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private static final Set<User> users = new HashSet<>();

    public User create(final User user) {
        validate(user);
        user.setId((long) users.size() + 1);
        users.add(user);
        return user;
    }

    public User getById(final Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format("Пользователь по ID=%d не найден!", id)));
    }

    public User update(final User user) {
        validate(user);
        final User existUser = getById(user.getId());
        if (existUser == null) {
            return null;
        }
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        return existUser;
    }

    public void delete(final Long id) {
        users.remove(getById(id));
    }

    private void validate(final User user) {
        if (user == null) {
            throw new BadRequestException("Введите информацию о пользователе");
        }
        if (!StringUtils.hasText(user.getName())) {
            throw new BadRequestException("Укажите имя пользователя!");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new BadRequestException("Укажите email пользователя!");
        }
    }
}
