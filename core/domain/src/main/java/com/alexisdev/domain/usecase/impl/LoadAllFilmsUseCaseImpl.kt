package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.repository.FilmRepository
import com.alexisdev.domain.usecase.api.LoadAllFilmsUseCase

internal class LoadAllFilmsUseCaseImpl(private val filmRepository: FilmRepository) : LoadAllFilmsUseCase {
    override fun execute() = filmRepository.loadAllFilms()
}