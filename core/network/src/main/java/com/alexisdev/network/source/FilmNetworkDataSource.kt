package com.alexisdev.network.source

import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.model.GenreDto
import kotlinx.coroutines.flow.Flow

interface FilmNetworkDataSource {
    fun fetchFilms()
    fun getFilms(): Flow<List<FilmDto>>
    suspend fun getGenres(): Flow<List<GenreDto>>
    fun loadFilmsByGenre(genre: GenreDto)
    fun getFilmDetails(id: Int): Flow<FilmDto>
}