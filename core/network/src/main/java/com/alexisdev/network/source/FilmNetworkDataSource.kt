package com.alexisdev.network.source

import com.alexisdev.common.Response
import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.model.GenreDto
import kotlinx.coroutines.flow.Flow

interface FilmNetworkDataSource {
    fun getFilms(): Flow<Response<List<FilmDto>>>
    suspend fun getGenres(): Flow<Response<List<GenreDto>>>
    fun loadFilmsByGenre(genre: GenreDto?)
    fun getFilmDetails(id: Int): Flow<Response<FilmDto>>
    fun refresh()
    fun getSelectedGenre(): Flow<GenreDto?>
}