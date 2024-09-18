package com.alexisdev.domain.usecase.api

import com.alexisdev.domain.model.Genre

interface LoadFilmsByGenreUseCase {
    fun execute(genre: Genre)
}