package com.codek.loginapp.presentation.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.loginapp.Screen
import com.codek.loginapp.presentation.login.ui.animation.enterTransition
import com.codek.loginapp.presentation.login.ui.animation.exitTransition
import com.codek.loginapp.presentation.login.ui.animation.popEnterTransition
import com.codek.loginapp.presentation.login.ui.animation.popExitTransition
import com.codek.loginapp.presentation.login.ui.screens.SplashScreen

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