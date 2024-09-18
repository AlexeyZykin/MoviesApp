package com.alexisdev.common.navigation

import androidx.navigation.NavDirections

sealed interface NavEffect {
    data class NavigateSingleTopTo(val navDirections: NavDirections) : NavEffect
    data class NavigateTo(val navDirections: NavDirections) : NavEffect
    data object NavigateUp : NavEffect
}