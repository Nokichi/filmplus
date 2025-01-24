package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.service.UserService;

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
}
