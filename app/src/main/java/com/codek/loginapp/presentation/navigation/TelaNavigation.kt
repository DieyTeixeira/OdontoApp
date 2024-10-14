package com.codek.loginapp.presentation.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.loginapp.Screen
import com.codek.loginapp.presentation.ui.animation.enterTransition
import com.codek.loginapp.presentation.ui.animation.exitTransition
import com.codek.loginapp.presentation.ui.animation.popEnterTransition
import com.codek.loginapp.presentation.ui.animation.popExitTransition
import com.codek.loginapp.presentation.ui.screens.TelaScreen
import com.codek.loginapp.presentation.viewmodel.RegisterViewModel
import com.codek.loginapp.presentation.viewmodel.SignOutViewModel
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