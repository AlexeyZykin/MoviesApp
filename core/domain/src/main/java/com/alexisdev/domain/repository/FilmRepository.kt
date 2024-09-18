package com.alexisdev.domain.repository

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getAllFilms(): Flow<List<Film>>
    fun loadAllFilms()
    fun loadFilmsByGenre(genre: Genre)
    fun getFilmDetails(id: Int): Flow<Film>
    suspend fun getAllGenres(): Flow<List<Genre>>
}