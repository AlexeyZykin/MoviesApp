package com.alexisdev.domain.di

import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import com.alexisdev.domain.usecase.api.LoadAllFilmsUseCase
import com.alexisdev.domain.usecase.api.LoadFilmsByGenreUseCase
import com.alexisdev.domain.usecase.impl.GetAllFilmsUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetAllGenresUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetFilmDetailsUseCaseImpl
import com.alexisdev.domain.usecase.impl.LoadAllFilmsUseCaseImpl
import com.alexisdev.domain.usecase.impl.LoadFilmsByGenreUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllFilmsUseCase> { GetAllFilmsUseCaseImpl(get()) }
    factory<GetAllGenresUseCase> { GetAllGenresUseCaseImpl(get()) }
    factory<GetFilmDetailsUseCase> { GetFilmDetailsUseCaseImpl(get()) }
    factory<LoadAllFilmsUseCase> { LoadAllFilmsUseCaseImpl(get()) }
    factory<LoadFilmsByGenreUseCase> { LoadFilmsByGenreUseCaseImpl(get()) }
}