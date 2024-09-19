package com.alexisdev.domain.usecase.api

import com.alexisdev.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GetSelectedGenreUseCase {
    fun execute(): Flow<Genre?>
}