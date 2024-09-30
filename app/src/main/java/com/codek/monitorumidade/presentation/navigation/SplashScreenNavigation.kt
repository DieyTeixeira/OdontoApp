package com.codek.monitorumidade.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.monitorumidade.presentation.ui.screens.SplashScreen

const val splashScreenRoute = "splashscreen"

fun NavGraphBuilder.splashScreen() {
    composable(splashScreenRoute) {
        SplashScreen()
    }
}