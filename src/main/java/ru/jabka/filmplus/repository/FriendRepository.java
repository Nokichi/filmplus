package ru.jabka.filmplus.repository;

import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.model.User;

import java.util.Map;
import java.util.Set;

@Repository
public class FriendRepository {
    private final Map<Long, Set<User>> friendList;

    public FriendRepository(Map<Long, Set<User>> friendList) {
        this.friendList = friendList;
    }

    public Map<Long, Set<User>> getFriendList() {
        return this.friendList;
    }
}