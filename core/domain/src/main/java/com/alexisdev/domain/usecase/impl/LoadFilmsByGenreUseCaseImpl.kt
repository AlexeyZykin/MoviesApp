package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repository.FilmRepository
import com.alexisdev.domain.usecase.api.LoadFilmsByGenreUseCase

internal class LoadFilmsByGenreUseCaseImpl(private val filmRepository: FilmRepository) :
    LoadFilmsByGenreUseCase {
    override fun execute(genre: Genre?) {
        filmRepository.loadFilmsByGenre(genre)
    }
}