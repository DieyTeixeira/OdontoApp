package com.codek.monitorumidade.presentation.navigation

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.monitorumidade.Screen
import com.codek.monitorumidade.presentation.ui.animation.enterTransition
import com.codek.monitorumidade.presentation.ui.animation.exitTransition
import com.codek.monitorumidade.presentation.ui.animation.popEnterTransition
import com.codek.monitorumidade.presentation.ui.animation.popExitTransition
import com.codek.monitorumidade.presentation.ui.screens.RegisterScreen
import com.codek.monitorumidade.presentation.ui.screens.SignInScreen
import com.codek.monitorumidade.presentation.viewmodel.RegisterViewModel
import com.codek.monitorumidade.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.registerScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = Screen.Register.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        val viewModel = koinViewModel<RegisterViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()

        RegisterScreen(
            viewModel = viewModel,
            uiState = uiState,
            onCreateClick = {
                scope.launch {
                    viewModel.register()
                }
            },
            onBackClick = {
                scope.launch {
                    viewModel.loadSavedCredentials()
                }
                onBackClick()
            }
        )
    }
}