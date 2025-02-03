package ru.jabka.filmplus.model;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String login;
    private LocalDate birthDay;
    private String email;

    public User(Long id, String name, String login, LocalDate birthDay, String email) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.birthDay = birthDay;
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLogin() {
        return this.login;
    }

    public LocalDate getBirthDay() {
        return this.birthDay;
    }

    public String getEmail() {
        return this.email;
    }
}