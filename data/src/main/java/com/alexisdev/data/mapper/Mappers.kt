package com.alexisdev.data.mapper

import com.alexisdev.data.network.model.FilmDto
import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre

fun FilmDto.toFilm() = Film(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres.map { genreFromStr(it) }
)

fun Film.toFilmDto() = FilmDto(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres.map { genreToStr(it) }
)

internal fun genreFromStr(genreStr: String): Genre {
    return when (genreStr) {
        "биография" -> Genre.BIOGRAPHY
        "боевик" -> Genre.ACTION
        "детектив" -> Genre.DETECTIVE
        "драма" -> Genre.DRAMA
        "комедия" -> Genre.COMEDY
        "криминал" -> Genre.CRIME
        "мелодрама" -> Genre.ROMANCE
        "мюзикл" -> Genre.MUSICAL
        "приключения" -> Genre.ADVENTURE
        "триллер" -> Genre.THRILLER
        "ужасы" -> Genre.HORROR
        "фантастика" -> Genre.SCI_FI
        else -> Genre.UNDEFINED
    }
}

internal fun genreToStr(genre: Genre): String {
    return when (genre) {
        Genre.BIOGRAPHY -> "биография"
        Genre.ACTION -> "боевик"
        Genre.DETECTIVE -> "детектив"
        Genre.DRAMA -> "драма"
        Genre.COMEDY -> "комедия"
        Genre.CRIME -> "криминал"
        Genre.ROMANCE -> "мелодрама"
        Genre.MUSICAL -> "мюзикл"
        Genre.ADVENTURE -> "приключения"
        Genre.THRILLER -> "триллер"
        Genre.HORROR -> "ужасы"
        Genre.SCI_FI -> "фантастика"
        else -> ""
    }
}