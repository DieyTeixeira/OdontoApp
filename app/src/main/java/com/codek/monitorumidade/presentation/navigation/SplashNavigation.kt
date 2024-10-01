package com.codek.monitorumidade.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.monitorumidade.Screen
import com.codek.monitorumidade.presentation.ui.animation.enterTransition
import com.codek.monitorumidade.presentation.ui.animation.exitTransition
import com.codek.monitorumidade.presentation.ui.animation.popEnterTransition
import com.codek.monitorumidade.presentation.ui.animation.popExitTransition
import com.codek.monitorumidade.presentation.ui.screens.SplashScreen

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