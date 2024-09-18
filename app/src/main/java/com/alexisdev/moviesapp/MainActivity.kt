package com.alexisdev.moviesapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.alexisdev.common.getKoinInstance
import com.alexisdev.common.navigation.NavEffect
import com.alexisdev.common.navigation.NavigationManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val navManager: NavigationManager = getKoinInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }


    private fun observeNavigationI() {
        lifecycleScope.launch {
            navManager.navEffectFlow.collect { navEffect ->
                when (navEffect) {
                    is NavEffect.NavigateSingleTopTo -> {
                        navController.navigate(
                            directions = navEffect.navDirections,
                            navOptions = null
                        )
                    }
                    is NavEffect.NavigateTo -> {
                        navController.navigate(
                            directions = navEffect.navDirections
                        )
                    }

                    is NavEffect.NavigateUp -> {
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}