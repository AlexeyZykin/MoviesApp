package com.alexisdev.film_details.di

import com.alexisdev.film_details.FilmDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val filmDetailsFeatureModule = module {
    viewModelOf(::FilmDetailsViewModel)
}