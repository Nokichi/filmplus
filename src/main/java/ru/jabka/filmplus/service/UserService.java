package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.UserRepository;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(final User user) {
        validateUser(user);
        user.setId(userRepository.getNextIndex());
        userRepository.getUsers().add(user);
        return user;
    }

    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    public User update(final User user) {
        validateUser(user);
        User existUser = userRepository.getById(user.getId());
        existUser.setName(user.getName());
        existUser.setLogin(user.getLogin());
        existUser.setBirthDay(user.getBirthDay());
        existUser.setEmail(user.getEmail());
        return existUser;
    }

    public void delete(final Long id) {
        userRepository.getUsers().remove(userRepository.getById(id));
    }

    private void validateUser(final User user) {
        ofNullable(user).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе"));
        if (!StringUtils.hasText(user.getName())) {
            throw new BadRequestException("Укажите имя пользователя!");
        }
        if (!StringUtils.hasText(user.getLogin())) {
            throw new BadRequestException("Укажите логин пользователя!");
        }
        ofNullable(user.getBirthDay()).orElseThrow(() -> new BadRequestException("Укажите дату рождения пользователя!"));
        if (user.getBirthDay().isAfter(LocalDate.now())) {
            throw new BadRequestException("Дата рождения пользователя не может быть больше текущей!");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new BadRequestException("Укажите email пользователя!");
        }
    }
}