package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.UserRepository;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void create_success() {
        final User user = getValidUser();
        Mockito.when(userRepository.insert(user)).thenReturn(user);
        User result = userService.create(user);
        Assertions.assertEquals(user, result);
        Mockito.verify(userRepository).insert(user);
    }

    @Test
    void update_success() {
        final User user = getValidUser();
        Mockito.when(userRepository.update(user)).thenReturn(user);
        User result = userService.update(user);
        Assertions.assertEquals(user, result);
        Mockito.verify(userRepository).update(user);
    }

    @Test
    void getById_success() {
        final User user = getValidUser();
        Mockito.when(userRepository.getById(user.id())).thenReturn(user);
        User result = userService.getById(user.id());
        Assertions.assertEquals(user, result);
        Mockito.verify(userRepository).getById(user.id());
    }

    @Test
    void create_error_nullUser() {
        final User user = null;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Введите информацию о пользователе");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullName() {
        final User user = User.builder()
                .id(2L)
                .name(null)
                .email("vito@mail.ru")
                .login("vito")
                .birthDay(LocalDate.of(1991, 2, 3))
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите имя пользователя!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullLogin() {
        final User user = User.builder()
                .id(2L)
                .name("Витька")
                .email("vito@mail.ru")
                .login(null)
                .birthDay(LocalDate.of(1991, 2, 3))
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите логин пользователя!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullBirthDay() {
        final User user = User.builder()
                .id(2L)
                .name("Витька")
                .email("vito@mail.ru")
                .login("vito")
                .birthDay(null)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите дату рождения пользователя!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_birthDayAfterNow() {
        final User user = User.builder()
                .id(2L)
                .name("Витька")
                .email("vito@mail.ru")
                .login("vito")
                .birthDay(LocalDate.now().plusDays(1))
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Дата рождения пользователя не может быть больше текущей!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullEmail() {
        final User user = User.builder()
                .id(2L)
                .name("Витька")
                .email(null)
                .login("vito")
                .birthDay(LocalDate.of(1991, 2, 3))
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите email пользователя!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullUser() {
        final User user = null;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.update(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Введите информацию о пользователе");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullName() {
        final User user = User.builder()
                .id(2L)
                .name(null)
                .email("vito@mail.ru")
                .login("vito")
                .birthDay(LocalDate.of(1991, 2, 3))
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.update(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите имя пользователя!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullLogin() {
        final User user = User.builder()
                .id(2L)
                .name("Витька")
                .email("vito@mail.ru")
                .login(null)
                .birthDay(LocalDate.of(1991, 2, 3))
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.update(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите логин пользователя!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullBirthDay() {
        final User user = User.builder()
                .id(2L)
                .name("Витька")
                .email("vito@mail.ru")
                .login("vito")
                .birthDay(null)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.update(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите дату рождения пользователя!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_birthDayAfterNow() {
        final User user = User.builder()
                .id(2L)
                .name("Витька")
                .email("vito@mail.ru")
                .login("vito")
                .birthDay(LocalDate.now().plusDays(1))
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.update(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Дата рождения пользователя не может быть больше текущей!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullEmail() {
        final User user = User.builder()
                .id(2L)
                .name("Витька")
                .email(null)
                .login("vito")
                .birthDay(LocalDate.of(1991, 2, 3))
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.update(user)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите email пользователя!");
        Mockito.verify(userRepository, Mockito.never()).insert(Mockito.any());
    }

    private User getValidUser() {
        return User.builder()
                .id(1L)
                .name("Ванька")
                .email("vano@mail.ru")
                .login("vano")
                .birthDay(LocalDate.of(1991, 2, 3))
                .build();
    }
}