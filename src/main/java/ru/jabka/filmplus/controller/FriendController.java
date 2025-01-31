package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.FriendRequest;
import ru.jabka.filmplus.service.FriendService;

@RestController
@Tag(name = "Друзья")
@RequestMapping("/api/v1/friend")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @Operation(summary = "Добавить пользователя в друзья")
    @PostMapping("/friend")
    public void addFriend(@RequestBody FriendRequest friendRequest) {
        friendService.addFriend(friendRequest);
    }
}