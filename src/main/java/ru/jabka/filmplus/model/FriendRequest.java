package ru.jabka.filmplus.model;

public class FriendRequest {
    private Long userId;
    private Long friendId;

    public FriendRequest(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getFriendId() {
        return this.friendId;
    }
}