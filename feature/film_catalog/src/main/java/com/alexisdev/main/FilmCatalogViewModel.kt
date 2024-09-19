package com.alexisdev.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavDirections
import com.alexisdev.common.Response
import com.alexisdev.common.navigation.NavEffect
import com.alexisdev.common.navigation.NavigationManager
import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import com.alexisdev.domain.usecase.api.GetSelectedGenreUseCase
import com.alexisdev.domain.usecase.api.LoadFilmsByGenreUseCase
import com.alexisdev.domain.usecase.api.RefreshUseCase
import com.alexisdev.main.mapper.toFilmUi
import com.alexisdev.main.mapper.toGenre
import com.alexisdev.main.mapper.toGenreUi
import com.alexisdev.main.model.FilmUi
import com.alexisdev.main.model.GenreUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmCatalogViewModel(
    private val getAllFilmsUseCase: GetAllFilmsUseCase,
    private val getAllGenresUseCase: GetAllGenresUseCase,
    private val getSelectedGenreUseCase: GetSelectedGenreUseCase,
    private val refreshUseCase: RefreshUseCase,
    private val loadFilmsByGenreUseCase: LoadFilmsByGenreUseCase,
    private val navManager: NavigationManager
) : ViewModel() {
    private val _uiState: MutableStateFlow<FilmCatalogState> =
        MutableStateFlow(FilmCatalogState.Loading)
    val uiState: StateFlow<FilmCatalogState> = _uiState

    init {
        getAllFilmCatalogData()
        loadAllFilmCatalogData()
    }

    private fun loadAllFilmCatalogData() {
        loadFilmsByGenreUseCase.execute(null)
    }

    private fun getAllFilmCatalogData() = viewModelScope.launch {
        combine(
            getAllFilmsUseCase.execute(),
            getAllGenresUseCase.execute(),
            getSelectedGenreUseCase.execute()
        ) { filmsResponse, genresResponse, selectedGenre ->
            when {
                filmsResponse is Response.Loading || genresResponse is Response.Loading -> {
                    _uiState.update { FilmCatalogState.Loading }
                }

                filmsResponse is Response.Error -> {
                    _uiState.update {
                        FilmCatalogState.Error(msg = filmsResponse.message)
                    }
                }

                genresResponse is Response.Error -> {
                    _uiState.update {
                        FilmCatalogState.Error(genresResponse.message)
                    }
                }

                filmsResponse is Response.Success && genresResponse is Response.Success -> {
                    _uiState.value = FilmCatalogState.Content(
                        genres = genresResponse.data.map { it.toGenreUi() },
                        films = filmsResponse.data.map { it.toFilmUi() },
                        selectedGenre = selectedGenre?.toGenreUi()
                    )
                }
            }
        }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: FilmCatalogEvent) {
        when (event) {
            is FilmCatalogEvent.OnSelectGenre -> { handleOnSelectGenre(event.genre) }

            is FilmCatalogEvent.OnFilmClick -> {
                navManager.navigate(NavEffect.NavigateTo(event.navDirections))
            }

            is FilmCatalogEvent.OnRetry -> { handleOnRetry() }
        }
    }

    private fun handleOnSelectGenre(genreUi: GenreUi) {
        (_uiState.value as? FilmCatalogState.Content)?.let { content ->
            if (genreUi == content.selectedGenre) {
                _uiState.update {
                    (it as? FilmCatalogState.Content)?.copy(selectedGenre = null) ?: it
                }
                loadFilmsByGenreUseCase.execute(null)
            } else {
                _uiState.update {
                    (it as? FilmCatalogState.Content)?.copy(selectedGenre = genreUi) ?: it
                }
                loadFilmsByGenreUseCase.execute(genreUi.toGenre())
            }
        }
    }

    private fun handleOnRetry() {
        _uiState.onEach {
            (it as? FilmCatalogState.Error)?.let {
                refreshUseCase.execute()
            }
        }.launchIn(viewModelScope)
    }
}


sealed interface FilmCatalogState {
    data class Content(
        val genres: List<GenreUi>,
        val films: List<FilmUi>,
        val selectedGenre: GenreUi? = null
    ) : FilmCatalogState

    data object Loading : FilmCatalogState
    data class Error(val msg: String) : FilmCatalogState
}

sealed interface FilmCatalogEvent {
    data class OnSelectGenre(val genre: GenreUi) : FilmCatalogEvent
    data class OnFilmClick(val navDirections: NavDirections) : FilmCatalogEvent
    data object OnRetry : FilmCatalogEvent
}