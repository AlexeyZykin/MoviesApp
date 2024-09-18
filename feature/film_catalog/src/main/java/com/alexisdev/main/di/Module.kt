package com.alexisdev.main.di

import com.alexisdev.main.FilmCatalogViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val filmCatalogFeatureModule = module {
    viewModelOf(::FilmCatalogViewModel)
}