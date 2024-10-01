package com.codek.monitorumidade.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.monitorumidade.Screen
import com.codek.monitorumidade.presentation.ui.animation.enterTransition
import com.codek.monitorumidade.presentation.ui.animation.exitTransition
import com.codek.monitorumidade.presentation.ui.animation.popEnterTransition
import com.codek.monitorumidade.presentation.ui.animation.popExitTransition
import com.codek.monitorumidade.presentation.ui.screens.AppAgroScreen

fun NavGraphBuilder.appAgroScreen() {
    composable(
        route = Screen.AppAgro.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppAgroScreen()
        }
    }
}
