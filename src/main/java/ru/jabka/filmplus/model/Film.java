package ru.jabka.filmplus.model;

import java.time.LocalDate;
import java.util.Set;

public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer durationInSeconds;
    private Set<GenreEnum> genres;

    public Film(Long id, String name, String description, LocalDate releaseDate, Integer durationInSeconds, Set<GenreEnum> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.durationInSeconds = durationInSeconds;
        this.genres = genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setGenres(Set<GenreEnum> genres) {
        this.genres = genres;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public Integer getDurationInSeconds() {
        return this.durationInSeconds;
    }

    public Set<GenreEnum> getGenres() {
        return this.genres;
    }
}