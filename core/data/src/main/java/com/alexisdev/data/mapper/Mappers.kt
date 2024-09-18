package com.alexisdev.data.mapper

import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.model.GenreDto
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
    genres = genres.map { it.toGenre() }
)

fun Film.toFilmDto() = FilmDto(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres.map { it.toGenreDto() }
)

fun GenreDto.toGenre(): Genre {
    return Genre.valueOf(this.name)
}

fun Genre.toGenreDto(): GenreDto {
    return GenreDto.valueOf(this.name)
}
