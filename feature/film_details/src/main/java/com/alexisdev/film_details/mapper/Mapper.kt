package com.alexisdev.film_details.mapper

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import com.alexisdev.film_details.model.FilmUi
import com.alexisdev.film_details.model.GenreUi

fun Film.toFilmUi() = FilmUi(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = Math.round(rating * 10.0) / 10.0,
    imageUrl = imageUrl,
    description = description,
    genres = genres.map { it.toGenreUi() }
)

fun FilmUi.toFilm() = Film(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres.map { it.toGenre() }
)

fun Genre.toGenreUi(): GenreUi {
    return when (this) {
        Genre.BIOGRAPHY -> GenreUi.BIOGRAPHY
        Genre.ACTION -> GenreUi.ACTION
        Genre.DETECTIVE -> GenreUi.DETECTIVE
        Genre.DRAMA -> GenreUi.DRAMA
        Genre.COMEDY -> GenreUi.COMEDY
        Genre.CRIME -> GenreUi.CRIME
        Genre.ROMANCE -> GenreUi.ROMANCE
        Genre.MUSICAL -> GenreUi.MUSICAL
        Genre.ADVENTURE -> GenreUi.ADVENTURE
        Genre.THRILLER -> GenreUi.THRILLER
        Genre.HORROR -> GenreUi.HORROR
        Genre.SCI_FI -> GenreUi.SCI_FI
        Genre.FANTASY -> GenreUi.FANTASY
    }
}

fun GenreUi.toGenre(): Genre {
    return when (this) {
        GenreUi.BIOGRAPHY -> Genre.BIOGRAPHY
        GenreUi.ACTION -> Genre.ACTION
        GenreUi.DETECTIVE -> Genre.DETECTIVE
        GenreUi.DRAMA -> Genre.DRAMA
        GenreUi.COMEDY -> Genre.COMEDY
        GenreUi.CRIME -> Genre.CRIME
        GenreUi.ROMANCE -> Genre.ROMANCE
        GenreUi.MUSICAL -> Genre.MUSICAL
        GenreUi.ADVENTURE -> Genre.ADVENTURE
        GenreUi.THRILLER -> Genre.THRILLER
        GenreUi.HORROR -> Genre.HORROR
        GenreUi.SCI_FI -> Genre.SCI_FI
        GenreUi.FANTASY -> Genre.FANTASY
    }
}