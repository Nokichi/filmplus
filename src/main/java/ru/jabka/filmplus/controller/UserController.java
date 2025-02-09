package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.service.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Пользователи")
@RequestMapping("/api/v1/user")
public class UserController {
    public final UserService userService;

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
}