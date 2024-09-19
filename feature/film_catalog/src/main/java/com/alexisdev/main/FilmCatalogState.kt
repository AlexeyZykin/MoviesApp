package com.alexisdev.main

import com.alexisdev.main.model.FilmUi
import com.alexisdev.main.model.GenreUi

sealed interface FilmCatalogState {
    data class Content(
        val genres: List<GenreUi>,
        val films: List<FilmUi>,
        val selectedGenre: GenreUi? = null
    ) : FilmCatalogState

    data object Loading : FilmCatalogState
    data class Error(val msg: String) : FilmCatalogState
}