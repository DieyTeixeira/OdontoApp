package com.codek.monitorumidade.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.codek.monitorumidade.presentation.ui.screens.SignInScreen
import com.codek.monitorumidade.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.signInScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    composable(
        route = Screen.SignIn.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()

        LaunchedEffect(viewModel.signInIsSucessful) {
            viewModel.signInIsSucessful.collect { success ->
                if (success) {
                    onLoginSuccess()
                }
            }
        }

        SignInScreen(
            viewModel = viewModel,
            uiState = uiState,
            onSignInClick = {
                scope.launch {
                    viewModel.signIn()
                }
            },
            onSignUpClick = onRegisterClick
        )
    }
}