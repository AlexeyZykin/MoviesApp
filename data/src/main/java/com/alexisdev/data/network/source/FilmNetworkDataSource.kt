package com.alexisdev.data.network.source

import com.alexisdev.data.network.model.FilmDto
import kotlinx.coroutines.flow.Flow

interface FilmNetworkDataSource {
    fun fetchFilms()
    fun getFilms(): Flow<List<FilmDto>>
}