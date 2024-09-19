package com.alexisdev.domain.repository

import com.alexisdev.common.Response
import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getAllFilms(): Flow<Response<List<Film>>>
    fun loadFilmsByGenre(genre: Genre?)
    fun getFilmDetails(id: Int): Flow<Response<Film>>
    suspend fun getAllGenres(): Flow<Response<List<Genre>>>
    fun getSelectedGenre(): Flow<Genre?>
    fun refresh()
}