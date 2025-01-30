package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.FriendRequest;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.FriendRepository;
import ru.jabka.filmplus.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public UserService(UserRepository userRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    public User create(final User user) {
        validateUser(user);
        user.setId(userRepository.getNextIndex());
        userRepository.getUsers().add(user);
        return user;
    }

    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    public User update(final User user) {
        validateUser(user);
        User existUser = userRepository.getById(user.getId());
        existUser.setName(user.getName());
        existUser.setLogin(user.getLogin());
        existUser.setBirthDay(user.getBirthDay());
        existUser.setEmail(user.getEmail());
        return existUser;
    }

    public void delete(final Long id) {
        userRepository.getUsers().remove(userRepository.getById(id));
    }

    public void addFriend(final FriendRequest friendRequest) {
        validateFriendRequest(friendRequest);
        User userById = userRepository.getById(friendRequest.getUserId());
        User friendById = userRepository.getById(friendRequest.getFriendId());
        Set<User> friends = ofNullable(friendRepository.getFriendList()
                .get(friendRequest.getUserId()))
                .orElse(new HashSet<>());
        friends.add(friendById);
        friendRepository.getFriendList().put(userById.getId(), friends);
        Set<User> friendsOfFriend = ofNullable(friendRepository.getFriendList()
                .get(friendRequest.getFriendId()))
                .orElse(new HashSet<>());
        friendsOfFriend.add(userById);
        friendRepository.getFriendList().put(friendById.getId(), friendsOfFriend);
    }

    public Set<User> getFriendsByUserId(final Long id) {
        userRepository.getById(id);
        return ofNullable(friendRepository.getFriendList().get(id)).orElse(Collections.emptySet());
    }

    private void validateUser(final User user) {
        ofNullable(user).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе"));
        if (!StringUtils.hasText(user.getName())) {
            throw new BadRequestException("Укажите имя пользователя!");
        }
        if (!StringUtils.hasText(user.getLogin())) {
            throw new BadRequestException("Укажите логин пользователя!");
        }
        ofNullable(user.getBirthDay()).orElseThrow(() -> new BadRequestException("Укажите дату рождения пользователя!"));
        if (user.getBirthDay().isAfter(LocalDate.now())) {
            throw new BadRequestException("Дата рождения пользователя не может быть больше текущей!");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new BadRequestException("Укажите email пользователя!");
        }
    }

    private void validateFriendRequest(final FriendRequest friendRequest) {
        ofNullable(friendRequest).orElseThrow(() -> new BadRequestException("Введите информацию о добавлении в друзья"));
        ofNullable(friendRequest.getUserId()).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе, который добавляет в друзья"));
        ofNullable(friendRequest.getFriendId()).orElseThrow(() -> new BadRequestException("Введите информацию о пользователе, которого добавляют в друзья"));
    }
}