package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.UserRepository;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Throwable.class)
    public User create(final User user) {
        validateUser(user);
        return userRepository.insert(user);
    }

    @Transactional(readOnly = true)
    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    public User update(final User user) {
        validateUser(user);
        return userRepository.update(user);
    }

    private void validateUser(final User user) {
        ofNullable(user).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе"));
        if (!StringUtils.hasText(user.name())) {
            throw new BadRequestException("Укажите имя пользователя!");
        }
        if (!StringUtils.hasText(user.login())) {
            throw new BadRequestException("Укажите логин пользователя!");
        }
        ofNullable(user.birthDay()).orElseThrow(() -> new BadRequestException("Укажите дату рождения пользователя!"));
        if (user.birthDay().isAfter(LocalDate.now())) {
            throw new BadRequestException("Дата рождения пользователя не может быть больше текущей!");
        }
        if (!StringUtils.hasText(user.email())) {
            throw new BadRequestException("Укажите email пользователя!");
        }
    }
}