package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.repository.MovieRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void create_success() {
        final Movie movie = getValidMovie();
        Mockito.when(movieRepository.insert(movie)).thenReturn(movie);
        Movie result = movieService.create(movie);
        Assertions.assertEquals(movie, result);
        Mockito.verify(movieRepository).insert(movie);
    }

    @Test
    void update_success() {
        final Movie movie = getValidMovie();
        Mockito.when(movieRepository.update(movie)).thenReturn(movie);
        Movie result = movieService.update(movie);
        Assertions.assertEquals(movie, result);
        Mockito.verify(movieRepository).update(movie);
    }

    @Test
    void getById_success() {
        final Movie movie = getValidMovie();
        Mockito.when(movieRepository.getById(movie.id())).thenReturn(movie);
        Movie result = movieService.getById(movie.id());
        Assertions.assertEquals(movie, result);
        Mockito.verify(movieRepository).getById(movie.id());
    }

    @Test
    void search_success() {
        final Movie movie = getValidMovie();
        String titleWrapper = "%" + movie.title() + "%";
        String descriptionWrapper = "%%";
        String genreWrapper = "%%";
        Mockito.when(movieRepository.search(titleWrapper, descriptionWrapper, genreWrapper, ""))
                .thenReturn(Collections.singletonList(movie));
        List<Movie> result = movieService.search(movie.title(), null, null, null);
        Assertions.assertEquals(Collections.singletonList(movie), result);
        Mockito.verify(movieRepository).search(titleWrapper, descriptionWrapper, genreWrapper, "");
    }

    @Test
    void search_success_full() {
        final Movie movie = getValidMovie();
        String titleWrapper = "%" + movie.title() + "%";
        String descriptionWrapper = "%" + movie.description() + "%";
        String genreWrapper = "%" + movie.genre() + "%";
        String releaseDateWrapper = movie.releaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Mockito.when(movieRepository.search(titleWrapper, descriptionWrapper, genreWrapper, releaseDateWrapper))
                .thenReturn(Collections.singletonList(movie));
        List<Movie> result = movieService.search(movie.title(), movie.description(), movie.genre(), movie.releaseDate());
        Assertions.assertEquals(Collections.singletonList(movie), result);
        Mockito.verify(movieRepository).search(titleWrapper, descriptionWrapper, genreWrapper, releaseDateWrapper);
    }

    @Test
    void create_error_nullMovie() {
        final Movie movie = null;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.create(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Введите информацию о фильме");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullTitle() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title(null)
                .description("Фильм по книге Фрэнка Герберта")
                .releaseDate(LocalDate.of(2021, 6, 6))
                .duration(150)
                .genre(Genre.SCIENCE_FICTION)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.create(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите название фильма");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullDescription() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title("Дюна")
                .description(null)
                .releaseDate(LocalDate.of(2021, 6, 6))
                .duration(150)
                .genre(Genre.SCIENCE_FICTION)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.create(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите описание фильма");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullReleaseDate() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title("Дюна")
                .description("Фильм по книге Фрэнка Герберта")
                .releaseDate(null)
                .duration(150)
                .genre(Genre.SCIENCE_FICTION)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.create(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите дату выхода фильма");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullDuration() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title("Дюна")
                .description("Фильм по книге Фрэнка Герберта")
                .releaseDate(LocalDate.of(2021, 6, 6))
                .duration(null)
                .genre(Genre.SCIENCE_FICTION)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.create(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите продолжительность фильма");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullGenre() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title("Дюна")
                .description("Фильм по книге Фрэнка Герберта")
                .releaseDate(LocalDate.of(2021, 6, 6))
                .duration(150)
                .genre(null)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.create(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите жанр, к которому относится фильм");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullMovie() {
        final Movie movie = null;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.update(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Введите информацию о фильме");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullTitle() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title(null)
                .description("Фильм по книге Фрэнка Герберта")
                .releaseDate(LocalDate.of(2021, 6, 6))
                .duration(150)
                .genre(Genre.SCIENCE_FICTION)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.update(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите название фильма");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullDescription() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title("Дюна")
                .description(null)
                .releaseDate(LocalDate.of(2021, 6, 6))
                .duration(150)
                .genre(Genre.SCIENCE_FICTION)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.update(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите описание фильма");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullReleaseDate() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title("Дюна")
                .description("Фильм по книге Фрэнка Герберта")
                .releaseDate(null)
                .duration(150)
                .genre(Genre.SCIENCE_FICTION)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.update(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите дату выхода фильма");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullDuration() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title("Дюна")
                .description("Фильм по книге Фрэнка Герберта")
                .releaseDate(LocalDate.of(2021, 6, 6))
                .duration(null)
                .genre(Genre.SCIENCE_FICTION)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.update(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите продолжительность фильма");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void update_error_nullGenre() {
        final Movie movie = Movie.builder()
                .id(2L)
                .title("Дюна")
                .description("Фильм по книге Фрэнка Герберта")
                .releaseDate(LocalDate.of(2021, 6, 6))
                .duration(150)
                .genre(null)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> movieService.update(movie)
        );
        Assertions.assertEquals(exception.getMessage(), "Укажите жанр, к которому относится фильм");
        Mockito.verify(movieRepository, Mockito.never()).insert(Mockito.any());
    }

    private Movie getValidMovie() {
        return Movie.builder()
                .id(1L)
                .title("Оно")
                .description("Фильм по книге Стивена Кинга")
                .releaseDate(LocalDate.of(2017, 3, 19))
                .duration(120)
                .genre(Genre.HORROR)
                .build();
    }
}