package ru.jabka.filmplus.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.GenreEnum;
import ru.jabka.filmplus.model.ReviewEntity;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.FilmRepository;
import ru.jabka.filmplus.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;

@Configuration
public class AppConfiguration {
    @Bean
    public Set<User> users() {
        return new HashSet<>();
    }

    @Bean
    public Set<Film> films() {
        return new HashSet<>();
    }

    @Bean
    public Map<Long, Set<User>> friendList() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, List<ReviewEntity>> reviews() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Set<User>> likes() {
        return new HashMap<>();
    }

    @Bean
    public CommandLineRunner dataLoader(
            Set<Film> films,
            Set<User> users,
            UserRepository userRepository,
            FilmRepository filmRepository) {
        return args -> {
            User victor = new User(
                    userRepository.getNextIndex(),
                    "Витька",
                    "vitoScall",
                    LocalDate.of(1994, 10, 25),
                    "vito_scaletta@mail.com");
            User micha = new User(
                    userRepository.getNextIndex(),
                    "Мишка",
                    "michaPot",
                    LocalDate.of(1995, 11, 12),
                    "micha_potap@mail.com");
            User dimka = new User(
                    userRepository.getNextIndex(),
                    "Димка",
                    "deamonster",
                    LocalDate.of(2000, 1, 4),
                    "deamonster@mail.com");
            users.addAll(Set.of(victor, micha, dimka));
            Film it = new Film(
                    filmRepository.getNextIndex(),
                    "Оно",
                    "Фильм по книге Стивена Кинга",
                    LocalDate.of(2017, 3, 20),
                    7200,
                    Set.of(GenreEnum.HORROR, GenreEnum.THRILLER));
            Film dune = new Film(
                    filmRepository.getNextIndex(),
                    "Дюна",
                    "Фильм по книге Фрэнка Герберта",
                    LocalDate.of(2021, 6, 6),
                    7200,
                    Set.of(GenreEnum.SCIENCE_FICTION, GenreEnum.ADVENTURE, GenreEnum.DRAMA));
            Film oneFlewOverTheCuckoosNest = new Film(
                    filmRepository.getNextIndex(),
                    "Пролетая над гнездом кукушки",
                    "Фильм по книге Кена Кизи",
                    LocalDate.of(1975, 5, 10),
                    7000,
                    Set.of(GenreEnum.DRAMA, GenreEnum.COMEDY));
            Film theGreenMile = new Film(
                    filmRepository.getNextIndex(),
                    "Зелёная миля",
                    "Фильм по книге Стивена Кинга",
                    LocalDate.of(1999, 2, 12),
                    10800,
                    Set.of(GenreEnum.DRAMA, GenreEnum.THRILLER)
            );
            films.addAll(List.of(it, dune, oneFlewOverTheCuckoosNest, theGreenMile));
        };
    }
}