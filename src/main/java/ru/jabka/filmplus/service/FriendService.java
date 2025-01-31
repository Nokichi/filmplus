package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.FriendRequest;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.FriendRepository;

import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Service
public class FriendService {
    private final UserService userService;
    private final FriendRepository friendRepository;

    public FriendService(UserService userService, FriendRepository friendRepository) {
        this.userService = userService;
        this.friendRepository = friendRepository;
    }

    public void addFriend(final FriendRequest friendRequest) {
        validateFriendRequest(friendRequest);
        User userById = userService.getById(friendRequest.getUserId());
        User friendById = userService.getById(friendRequest.getFriendId());
        setFriendship(userById, friendById);
        setFriendship(friendById, userById);
    }

    private void setFriendship(final User from, final User to) {
        Set<User> friends = ofNullable(friendRepository.getFriendList()
                .get(from.getId()))
                .orElse(new HashSet<>());
        friends.add(to);
        friendRepository.getFriendList().put(from.getId(), friends);
    }

    private void validateFriendRequest(final FriendRequest friendRequest) {
        ofNullable(friendRequest).orElseThrow(() -> new BadRequestException("Введите информацию о добавлении в друзья"));
        ofNullable(friendRequest.getUserId()).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе, который добавляет в друзья"));
        ofNullable(friendRequest.getFriendId()).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе, которого добавляют в друзья"));
    }
}