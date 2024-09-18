package com.alexisdev.data.repository

import com.alexisdev.data.mapper.toFilm
import com.alexisdev.data.mapper.toGenre
import com.alexisdev.data.mapper.toGenreDto
import com.alexisdev.network.api.FilmApiService
import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.source.FilmNetworkDataSource
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

    override fun loadAllFilms() {
        filmNetworkDataSource.fetchFilms()
    }

    override fun loadFilmsByGenre(genre: Genre) {
        filmNetworkDataSource.loadFilmsByGenre(genre.toGenreDto())
    }

    override fun getFilmDetails(id: Int): Flow<Film> {
        return filmNetworkDataSource.getFilmDetails(id).map { it.toFilm() }
    }

    override suspend fun getAllGenres(): Flow<List<Genre>> {
        return filmNetworkDataSource.getGenres().map { genres ->
            genres.map { it.toGenre() }
        }
    }
}