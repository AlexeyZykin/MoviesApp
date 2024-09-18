package com.alexisdev.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import com.alexisdev.domain.usecase.api.LoadAllFilmsUseCase
import com.alexisdev.domain.usecase.api.LoadFilmsByGenreUseCase
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
    private val loadAllFilmsUseCase: LoadAllFilmsUseCase,
    private val loadFilmsByGenreUseCase: LoadFilmsByGenreUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<FilmCatalogState> = MutableStateFlow(FilmCatalogState.Loading)
    val uiState: StateFlow<FilmCatalogState> = _uiState

    init {
        getAllFilmCatalogData()
        loadAllFilmCatalogData()
    }

    fun loadAllFilmCatalogData() {
        loadAllFilmsUseCase.execute()
    }

    private fun getAllFilmCatalogData() = viewModelScope.launch {
        combine(
            getAllFilmsUseCase.execute(),
            getAllGenresUseCase.execute()
        ) { films, genres ->
            _uiState.update {
                FilmCatalogState.Content(
                    genres = genres.map { it.toGenreUi() },
                    films = films.map { it.toFilmUi() },
                    selectedGenre = null
                )
            }
        }
            .catch { FilmCatalogState.Error(msg = it.localizedMessage ?: "Error") }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: FilmCatalogEvent) {
        when (event) {
            is FilmCatalogEvent.OnSelectGenre -> { handleOnSelectGenre(event.genre) }
            is FilmCatalogEvent.OnFilmClick -> {}
        }
    }

    private fun handleOnSelectGenre(genreUi: GenreUi) {
        Log.d("viewModel", "eventFilter")
        loadFilmsByGenreUseCase.execute(genreUi.toGenre())
        _uiState.update {
            (it as? FilmCatalogState.Content)?.copy(selectedGenre = genreUi) ?: it
        }
    }
}

sealed interface FilmCatalogState {
    data class Content(
        val genres: List<GenreUi>,
        val films: List<FilmUi>,
        val selectedGenre: GenreUi?
        ) : FilmCatalogState
    data object Loading : FilmCatalogState
    data class Error(val msg: String) : FilmCatalogState
}

sealed interface FilmCatalogEvent {
    data class OnSelectGenre(val genre: GenreUi) : FilmCatalogEvent
    data class OnFilmClick(val filmId: Int) : FilmCatalogEvent
}