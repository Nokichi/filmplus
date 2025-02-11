package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.FriendRequest;
import ru.jabka.filmplus.repository.FriendRepository;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    @Transactional(rollbackFor = Throwable.class)
    public void addFriend(final FriendRequest friendRequest) {
        validateFriendRequest(friendRequest);
        friendRepository.insert(friendRequest);
    }

    private void validateFriendRequest(final FriendRequest friendRequest) {
        ofNullable(friendRequest).orElseThrow(() -> new BadRequestException("Введите информацию о добавлении в друзья"));
        ofNullable(friendRequest.userId()).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе, который добавляет в друзья"));
        ofNullable(friendRequest.friendId()).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе, которого добавляют в друзья"));
        if (friendRequest.userId().equals(friendRequest.friendId())) {
            throw new BadRequestException("Ошибка: пользователь пытается добавить в друзья самого себя");
        }
    }
}