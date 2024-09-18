package com.alexisdev.network.source

import android.util.Log
import com.alexisdev.network.api.FilmApiService
import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.model.GenreDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

internal class FilmNetworkDataSourceImpl(private val filmApiService: FilmApiService) :
    FilmNetworkDataSource {
    private val state = MutableSharedFlow<GenreDto?>(extraBufferCapacity = 1, replay = 1)
    private val filmsFlow = state.flatMapLatest { filterGenre ->
        val films = filmApiService.getAllFilms().films
        val filteredFilms = if (filterGenre != null) {
            films.filter { film -> film.genres.contains(filterGenre) }
        } else {
            films
        }
        flowOf(filteredFilms)
    }

    override fun fetchFilms() {
        val signal = state.tryEmit(null)
    }

    override fun getFilms(): Flow<List<FilmDto>> {
        return filmsFlow
    }

    override suspend fun getGenres(): Flow<List<GenreDto>> {
        val genres = filmApiService.getAllFilms().films
            .flatMap { it.genres }.distinct()
        return flowOf(genres)
    }

    override fun loadFilmsByGenre(genre: GenreDto) {
        state.tryEmit(genre)
    }
}


//    private val filmsFlow = MutableSharedFlow<Unit>(0)
//    private val cachedFilms = filmsFlow.flatMapLatest {
//        flowOf(filmApiService.getAllFilms().films)
//    }
//    private val genresFlow = cachedFilms.flatMapLatest { films ->
//        val genres = films.flatMap { it.genres }.distinct()
//        flowOf(genres)
//    }
//    private val filterState = MutableSharedFlow<GenreDto>(replay = 1)
//    private val filteredFilms = filterState.flatMapLatest { filterGenre ->
//        val filteredFilms = filmApiService.getAllFilms().films
//            .filter { films ->
//                films.genres.contains(filterGenre)
//            }
//        flowOf(filteredFilms)
//    }