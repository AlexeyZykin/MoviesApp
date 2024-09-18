package com.alexisdev.moviesapp.di

import com.alexisdev.common.di.navigationModule
import com.alexisdev.data.di.dataModule
import com.alexisdev.domain.di.domainModule
import com.alexisdev.main.di.filmCatalogFeatureModule
import com.alexisdev.network.di.networkModule

val koinModules = listOf(
    networkModule,
    dataModule,
    domainModule,
    filmCatalogFeatureModule,
    navigationModule
)