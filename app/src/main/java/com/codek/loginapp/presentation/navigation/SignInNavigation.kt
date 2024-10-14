package com.codek.loginapp.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.loginapp.Screen
import com.codek.loginapp.presentation.ui.animation.enterTransition
import com.codek.loginapp.presentation.ui.animation.exitTransition
import com.codek.loginapp.presentation.ui.animation.popEnterTransition
import com.codek.loginapp.presentation.ui.animation.popExitTransition
import com.codek.loginapp.presentation.ui.screens.SignInScreen
import com.codek.loginapp.presentation.viewmodel.SignInViewModel
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