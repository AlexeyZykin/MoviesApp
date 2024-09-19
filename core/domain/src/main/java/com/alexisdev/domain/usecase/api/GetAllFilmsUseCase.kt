package com.alexisdev.domain.usecase.api

import com.alexisdev.common.Response
import com.alexisdev.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface GetAllFilmsUseCase {
    fun execute(): Flow<Response<List<Film>>>
}