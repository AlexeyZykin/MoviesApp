package com.alexisdev.data.repository

import com.alexisdev.data.mapper.toFilm
import com.alexisdev.data.network.api.FilmApiService
import com.alexisdev.data.network.model.FilmDto
import com.alexisdev.data.network.source.FilmNetworkDataSource
import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map

class FilmRepositoryImpl(private val filmNetworkDataSource: FilmNetworkDataSource) :
    FilmRepository {

    override fun getAllFilms(): Flow<List<Film>> {
        val filmsFlow = filmNetworkDataSource.getFilms().map { films ->
            films.map { it.toFilm() }
        }
        return filmsFlow
    }

    override fun loadFilmsByGenre(genres: List<Genre>): Flow<List<Film>> {
        TODO("Not yet implemented")
    }

    override fun getFilmDetails(id: Int): Flow<Film> {
        TODO("Not yet implemented")
    }

}