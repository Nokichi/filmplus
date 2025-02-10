package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.repository.MovieRepository;

import java.time.LocalDate;
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
        //TODO доделать поиск
        final Movie movie = getValidMovie();
        Mockito.when(movieRepository.search(movie.title(), null, null, null))
                .thenReturn(Collections.singletonList(movie));
        List<Movie> result = movieService.search(movie.title(), null, null, null);
        Assertions.assertEquals(Collections.singletonList(movie), result);
        Mockito.verify(movieRepository).search(movie.title(), null, null, null);
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