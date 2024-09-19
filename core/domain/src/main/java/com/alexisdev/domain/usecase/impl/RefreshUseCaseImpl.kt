package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.repository.FilmRepository
import com.alexisdev.domain.usecase.api.RefreshUseCase

internal class RefreshUseCaseImpl(private val filmRepository: FilmRepository) : RefreshUseCase {
    override fun execute() {
        filmRepository.refresh()
    }
}