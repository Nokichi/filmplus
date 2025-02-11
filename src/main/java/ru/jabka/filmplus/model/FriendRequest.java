package ru.jabka.filmplus.model;

public record FriendRequest(
        Long userId,
        Long friendId) {
}