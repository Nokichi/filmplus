package ru.jabka.filmplus.model;

import java.util.Date;

public class Film {
    private Long id;
    private String name;
    private String description;
    private Date releaseDate;
    private Integer durationInSeconds;
    private GenreEnum genre;

    public Film(Long id, String name, String description, Date releaseDate, Integer durationInSeconds, GenreEnum genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.durationInSeconds = durationInSeconds;
        this.genre = genre;
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

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public Integer getDurationInSeconds() {
        return this.durationInSeconds;
    }

    public GenreEnum getGenre() {
        return this.genre;
    }
}
