package com.alexisdev.domain.usecase.api

import com.alexisdev.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface GetAllFilmsUseCase {
    fun execute(): Flow<List<Film>>
}