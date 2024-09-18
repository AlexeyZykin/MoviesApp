package com.alexisdev.film_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import com.alexisdev.film_details.mapper.toFilmUi
import com.alexisdev.film_details.model.FilmUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

const val ARG_FILM_ID = "filmId"

class FilmDetailsViewModel(
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState: MutableStateFlow<FilmDetailsState> =
        MutableStateFlow(FilmDetailsState.Loading)
    val uiState: StateFlow<FilmDetailsState> = _uiState

    init {
        val filmId = savedStateHandle.get<Int>(ARG_FILM_ID)
            ?.also {
                getFilmDetailsUseCase.execute(it)
                    .onEach { film ->
                        _uiState.update {
                            FilmDetailsState.Content(filmUi = film.toFilmUi())
                        }
                    }
                    .launchIn(viewModelScope)
            }
    }
}

sealed interface FilmDetailsState {
    data class Content(
        val filmUi: FilmUi
    ) : FilmDetailsState

    data object Loading : FilmDetailsState
    data class Error(val msg: String) : FilmDetailsState
}