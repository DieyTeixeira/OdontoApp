package com.codek.odontoapp.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.odontoapp.Screen
import com.codek.odontoapp.splash.animation.enterTransition
import com.codek.odontoapp.splash.animation.exitTransition
import com.codek.odontoapp.splash.animation.popEnterTransition
import com.codek.odontoapp.splash.animation.popExitTransition
import com.codek.odontoapp.splash.screen.SplashScreen

fun NavGraphBuilder.splashScreen() {
    composable(
        route = Screen.Splash.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        SplashScreen()
    }
}