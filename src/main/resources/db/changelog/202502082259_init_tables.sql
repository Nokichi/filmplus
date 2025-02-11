CREATE SCHEMA filmplus;

CREATE TABLE filmplus.user
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR        NOT NULL,
    email    VARCHAR UNIQUE NOT NULL,
    login    VARCHAR UNIQUE NOT NULL,
    birthday DATE
);

CREATE TABLE filmplus.movie
(
    id           SERIAL PRIMARY KEY,
    title        VARCHAR NOT NULL,
    description  TEXT,
    release_date TIMESTAMP WITH TIME ZONE,
    duration     INT,
    genre        VARCHAR
);

CREATE TABLE filmplus.friend
(
    user_id   INT REFERENCES filmplus.user (id),
    friend_id INT REFERENCES filmplus.user (id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE filmplus.like
(
    user_id  INT REFERENCES filmplus.user (id),
    movie_id INT REFERENCES filmplus.movie (id),
    PRIMARY KEY (user_id, movie_id)
);

CREATE TABLE filmplus.review
(
    id          SERIAL PRIMARY KEY,
    user_id     INT REFERENCES filmplus.user (id),
    movie_id    INT REFERENCES filmplus.movie (id),
    review_text TEXT,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);