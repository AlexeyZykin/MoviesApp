package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repository.FilmRepository
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import kotlinx.coroutines.flow.Flow

internal class GetAllGenresUseCaseImpl(private val filmRepository: FilmRepository) :
    GetAllGenresUseCase {
    override suspend fun execute(): Flow<List<Genre>> = filmRepository.getAllGenres()
}