package com.alexisdev.domain.repository

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getAllFilms(): Flow<List<Film>>
    fun loadFilmsByGenre(genres: List<Genre>): Flow<List<Film>>
    fun getFilmDetails(id: Int): Flow<Film>
}