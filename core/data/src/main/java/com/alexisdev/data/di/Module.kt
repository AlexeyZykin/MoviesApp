package com.alexisdev.data.di

import com.alexisdev.data.repository.FilmRepositoryImpl
import com.alexisdev.domain.repository.FilmRepository
import org.koin.dsl.module

val dataModule = module {
    single <FilmRepository> { FilmRepositoryImpl(get()) }
}