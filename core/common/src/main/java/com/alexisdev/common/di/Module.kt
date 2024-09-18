package com.alexisdev.common.di

import com.alexisdev.common.navigation.NavigationManager
import org.koin.dsl.module

val navigationModule = module {
    single { NavigationManager() }
}