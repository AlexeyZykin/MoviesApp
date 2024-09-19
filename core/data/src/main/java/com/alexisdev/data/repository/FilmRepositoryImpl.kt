package com.alexisdev.data.repository

import com.alexisdev.common.Response
import com.alexisdev.data.mapper.toFilm
import com.alexisdev.data.mapper.toGenre
import com.alexisdev.data.mapper.toGenreDto
import com.alexisdev.network.api.FilmApiService
import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.source.FilmNetworkDataSource
import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repository.FilmRepository
import com.alexisdev.network.model.GenreDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class FilmRepositoryImpl(private val filmNetworkDataSource: FilmNetworkDataSource) :
    FilmRepository {

    override fun getAllFilms(): Flow<Response<List<Film>>> {
        val filmsFlow = filmNetworkDataSource.getFilms().map { response ->
            when (response) {
                is Response.Loading -> Response.Loading
                is Response.Error -> Response.Error(response.message)
                is Response.Success -> {
                    Response.Success(response.data.map { it.toFilm() })
                }
            }
        }
        return filmsFlow
    }

    override fun loadFilmsByGenre(genre: Genre?) {
        filmNetworkDataSource.loadFilmsByGenre(
            genre?.toGenreDto()
        )
    }

    override fun getFilmDetails(id: Int): Flow<Response<Film>> {
        return filmNetworkDataSource.getFilmDetails(id)
            .map { response ->
                when (response) {
                    is Response.Error -> Response.Error(response.message)
                    is Response.Loading -> Response.Loading
                    is Response.Success -> Response.Success(response.data.toFilm())
                }
            }
    }

    override suspend fun getAllGenres(): Flow<Response<List<Genre>>> {
        return filmNetworkDataSource.getGenres()
            .map { response ->
                when (response) {
                    is Response.Error -> Response.Error(response.message)
                    is Response.Loading -> Response.Loading
                    is Response.Success -> Response.Success(response.data.map { it.toGenre() })
                }
            }
    }

    override fun getSelectedGenre(): Flow<Genre?> {
        return filmNetworkDataSource.getSelectedGenre().map { it?.toGenre() }
    }

    override fun refresh() {
        filmNetworkDataSource.refresh()
    }
}