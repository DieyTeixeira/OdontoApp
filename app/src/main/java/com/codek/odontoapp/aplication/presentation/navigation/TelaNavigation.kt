package com.codek.odontoapp.aplication.presentation.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.odontoapp.Screen
import com.codek.odontoapp.aplication.presentation.ui.screen.TelaScreen
import com.codek.odontoapp.splash.animation.enterTransition
import com.codek.odontoapp.splash.animation.exitTransition
import com.codek.odontoapp.splash.animation.popEnterTransition
import com.codek.odontoapp.splash.animation.popExitTransition
import com.codek.odontoapp.login.presentation.viewmodel.SignOutViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.telaScreen(
    onSignOut: () -> Unit
) {
    composable(
        route = Screen.Tela.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        val viewModel = koinViewModel<SignOutViewModel>()
        val scope = rememberCoroutineScope()

        TelaScreen(
            onSignOut = {
                scope.launch {
                    viewModel.signOut()
                }
                onSignOut()
            }
        )
    }
}