package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.FriendRequest;
import ru.jabka.filmplus.repository.FriendRepository;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {
    @Mock
    private FriendRepository friendRepository;

    @InjectMocks
    private FriendService friendService;

    @Test
    void addFriend_success() {
        final FriendRequest friendRequest = new FriendRequest(1L, 2L);
        friendService.addFriend(friendRequest);
        Mockito.verify(friendRepository).insert(friendRequest);
    }

    @Test
    void addFriend_error_nullFriend() {
        final FriendRequest friendRequest = null;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> friendService.addFriend(friendRequest)
        );
        Assertions.assertEquals(exception.getMessage(), "Введите информацию о добавлении в друзья");
        Mockito.verify(friendRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void addFriend_error_nullUserId() {
        final FriendRequest friendRequest = new FriendRequest(null, 2L);
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> friendService.addFriend(friendRequest)
        );
        Assertions.assertEquals(exception.getMessage(), "Введите информацию о пользователе, который добавляет в друзья");
        Mockito.verify(friendRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void addFriend_error_nullFriendId() {
        final FriendRequest friendRequest = new FriendRequest(1L, null);
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> friendService.addFriend(friendRequest)
        );
        Assertions.assertEquals(exception.getMessage(), "Введите информацию о пользователе, которого добавляют в друзья");
        Mockito.verify(friendRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void addFriend_error_userIdEqualsFriendId() {
        final FriendRequest friendRequest = new FriendRequest(1L, 1L);
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> friendService.addFriend(friendRequest)
        );
        Assertions.assertEquals(exception.getMessage(), "Ошибка: пользователь пытается добавить в друзья самого себя");
        Mockito.verify(friendRepository, Mockito.never()).insert(Mockito.any());
    }
}