package com.alexisdev.film_details

import com.alexisdev.film_details.model.FilmUi

sealed interface FilmDetailsState {
    data class Content(
        val filmUi: FilmUi
    ) : FilmDetailsState

    data object Loading : FilmDetailsState
    data class Error(val msg: String) : FilmDetailsState
}