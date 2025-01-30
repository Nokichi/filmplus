package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.jabka.filmplus.model.FriendRequest;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")
public class UserController {
    public final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Создать пользователя")
    @PostMapping
    public User create(@RequestBody final User user) {
        return userService.create(user);
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Обновить данные пользователя")
    @PatchMapping
    public User update(@RequestBody final User user) {
        return userService.update(user);
    }

    @Operation(summary = "Удалить пользователя по ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(summary = "Добавить пользователя в друзья")
    @PostMapping("/friend")
    public void addFriend(@RequestBody FriendRequest friendRequest) {
        userService.addFriend(friendRequest);
    }

    @Operation(summary = "Получить список друзей пользователя")
    @GetMapping("/friends/{id}")
    public Set<User> getFriendsByUserId(@PathVariable Long id) {
        return userService.getFriendsByUserId(id);
    }
}