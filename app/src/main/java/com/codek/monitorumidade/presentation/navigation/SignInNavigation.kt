package com.codek.monitorumidade.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codek.monitorumidade.presentation.states.SignInUiState
import com.codek.monitorumidade.presentation.ui.screens.AppAgroScreen
import com.codek.monitorumidade.presentation.ui.screens.SignInScreen
import com.codek.monitorumidade.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val signInRoute = "signIn"

fun NavGraphBuilder.signInScreen(
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    composable(signInRoute) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()

        LaunchedEffect(viewModel.signInIsSucessful) {
            viewModel.signInIsSucessful.collect { success ->
                if (success) {
                    onLoginSuccess() // Navegar para a tela inicial
                }
            }
        }

        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                scope.launch {
                    viewModel.signIn()
                }
            },
            onSignUpClick = onSignUpClick
        )
    }
}

fun NavHostController.navigateToSignIn(
    navOptions: NavOptions? = null
) {
    navigate(signInRoute, navOptions)
}