package com.alexisdev.domain.usecase.impl

import com.alexisdev.common.Response
import com.alexisdev.domain.model.Film
import com.alexisdev.domain.repository.FilmRepository
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import kotlinx.coroutines.flow.Flow

internal class GetFilmDetailsUseCaseImpl(private val filmRepository: FilmRepository) :
    GetFilmDetailsUseCase {
    override fun execute(id: Int): Flow<Response<Film>> = filmRepository.getFilmDetails(id)
}