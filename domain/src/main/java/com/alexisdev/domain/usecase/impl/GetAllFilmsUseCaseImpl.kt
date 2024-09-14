package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.repository.FilmRepository
import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import kotlinx.coroutines.flow.Flow

internal class GetAllFilmsUseCaseImpl(private val filmRepository: FilmRepository) :
    GetAllFilmsUseCase {
    override fun execute(): Flow<List<Film>> = filmRepository.getAllFilms()
}