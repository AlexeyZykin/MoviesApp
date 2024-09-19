package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repository.FilmRepository
import com.alexisdev.domain.usecase.api.GetSelectedGenreUseCase
import kotlinx.coroutines.flow.Flow

internal class GetSelectedGenreUseCaseImpl(private val filmRepository: FilmRepository) :
    GetSelectedGenreUseCase {
    override fun execute(): Flow<Genre?> {
        return filmRepository.getSelectedGenre()
    }
}