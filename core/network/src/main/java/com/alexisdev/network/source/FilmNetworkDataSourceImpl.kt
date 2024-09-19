package com.alexisdev.network.source

import android.util.Log
import com.alexisdev.common.Response
import com.alexisdev.network.api.FilmApiService
import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.model.GenreDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.update
import java.io.IOException

internal class FilmNetworkDataSourceImpl(private val filmApiService: FilmApiService) :
    FilmNetworkDataSource {
    private val state = MutableSharedFlow<GenreDto?>(replay = 1)
    private val lastSelectedGenre = MutableStateFlow<GenreDto?>(null)
    private val filmsFlow = state.flatMapLatest { filterGenre ->
        flow<Response<List<FilmDto>>> {
            try {
                val films = filmApiService.getAllFilms().films
                    .sortedWith(
                        compareBy(String.CASE_INSENSITIVE_ORDER) { it.localizedName }
                    )
                val filteredFilms = if (filterGenre != null) {
                    films.filter { film -> film.genres.contains(filterGenre) }
                } else {
                    films
                }
                emit(Response.Success(data = filteredFilms))
            }
            catch (e: Exception) {
                Log.e("Error", e.message ?: "Unknown error")
                emit(Response.Error(e.message ?: "Error"))
            }
//                .catch {
//                    Log.e("Error", it.message ?: "Unknown error")
//                    emit(Response.Error(it.message ?: "Error"))
//                }
        }
    }

    override fun getFilms(): Flow<Response<List<FilmDto>>> {
        return filmsFlow
    }

    override suspend fun getGenres(): Flow<Response<List<GenreDto>>> {
        return flow<Response<List<GenreDto>>> {
            val genres = filmApiService.getAllFilms().films
                .flatMap { it.genres }.distinct()
            emit(Response.Success(genres))
        }
            .catch { emit(Response.Error(it.message ?: "Error")) }
    }

    override fun loadFilmsByGenre(genre: GenreDto?) {
        lastSelectedGenre.tryEmit(genre)
        val emit = state.tryEmit(genre)
        Log.d("network", emit.toString())
    }

    override fun getFilmDetails(id: Int): Flow<Response<FilmDto>> {
        return filmsFlow.flatMapLatest { response ->
            flow<Response<FilmDto>> {
                when (response) {
                    is Response.Error -> emit(Response.Error(response.message))
                    is Response.Loading -> emit(Response.Loading)
                    is Response.Success -> {
                        try {
                            val filmDetails = response.data.first { it.id == id }
                            emit(Response.Success(filmDetails))
                        } catch (e: Exception) {
                            emit(Response.Error(e.message ?: "Error"))
                        }
                    }
                }
            }
        }
    }

    override fun refresh() {
        state.tryEmit(lastSelectedGenre.value)
    }

    override fun getSelectedGenre(): Flow<GenreDto?> {
        return lastSelectedGenre
    }
}