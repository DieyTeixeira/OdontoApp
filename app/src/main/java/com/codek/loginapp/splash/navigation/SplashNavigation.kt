package com.codek.loginapp.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.loginapp.Screen
import com.codek.loginapp.splash.animation.enterTransition
import com.codek.loginapp.splash.animation.exitTransition
import com.codek.loginapp.splash.animation.popEnterTransition
import com.codek.loginapp.splash.animation.popExitTransition

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