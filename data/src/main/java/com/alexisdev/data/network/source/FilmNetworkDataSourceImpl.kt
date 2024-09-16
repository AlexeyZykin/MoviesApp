package com.alexisdev.data.network.source

import com.alexisdev.data.network.api.FilmApiService
import com.alexisdev.data.network.model.FilmDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class FilmNetworkDataSourceImpl(private val filmApiService: FilmApiService) :
    FilmNetworkDataSource {
    private val filmsFlow = MutableSharedFlow<Unit>(replay = 0)
    private val cachedFilms = filmsFlow.flatMapLatest {
        val films = filmApiService.getAllFilms().films
        flowOf(films)
    }

    override fun fetchFilms() {
        filmsFlow.tryEmit(Unit)
    }

    override fun getFilms(): Flow<List<FilmDto>> {
        return cachedFilms
    }


}