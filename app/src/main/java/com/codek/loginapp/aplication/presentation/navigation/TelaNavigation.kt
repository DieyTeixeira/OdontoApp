package com.codek.loginapp.aplication.presentation.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.loginapp.Screen
import com.codek.loginapp.aplication.presentation.ui.screen.TelaScreen
import com.codek.loginapp.splash.animation.enterTransition
import com.codek.loginapp.splash.animation.exitTransition
import com.codek.loginapp.splash.animation.popEnterTransition
import com.codek.loginapp.splash.animation.popExitTransition
import com.codek.loginapp.login.presentation.viewmodel.SignOutViewModel
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