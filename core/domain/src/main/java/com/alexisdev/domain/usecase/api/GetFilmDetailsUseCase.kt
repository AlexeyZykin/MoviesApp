package com.alexisdev.domain.usecase.api

import com.alexisdev.common.Response
import com.alexisdev.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface GetFilmDetailsUseCase {
    fun execute(id: Int): Flow<Response<Film>>
}