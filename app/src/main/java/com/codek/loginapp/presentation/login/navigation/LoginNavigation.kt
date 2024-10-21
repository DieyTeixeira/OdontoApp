package com.codek.loginapp.presentation.login.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.loginapp.Screen
import com.codek.loginapp.presentation.login.ui.animation.enterTransition
import com.codek.loginapp.presentation.login.ui.animation.exitTransition
import com.codek.loginapp.presentation.login.ui.animation.popEnterTransition
import com.codek.loginapp.presentation.login.ui.animation.popExitTransition
import com.codek.loginapp.presentation.login.ui.screens.LoginScreen
import com.codek.loginapp.presentation.login.ui.screens.SignInScreen
import com.codek.loginapp.presentation.login.viewmodel.RegisterViewModel
import com.codek.loginapp.presentation.login.viewmodel.SignInViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit
) {
    composable(
        route = Screen.Login.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        val viewModelSignIn = koinViewModel<SignInViewModel>()
        val viewModelRegister = koinViewModel<RegisterViewModel>()
        val uiStateSignIn by viewModelSignIn.uiState.collectAsState()
        val uiStateRegister by viewModelRegister.uiState.collectAsState()
        val scope = rememberCoroutineScope()

        LaunchedEffect(viewModelSignIn.signInIsSucessful) {
            viewModelSignIn.signInIsSucessful.collect { success ->
                if (success) {
                    onLoginSuccess()
                }
            }
        }

        LoginScreen(
            viewModelSignIn = viewModelSignIn,
            viewModelRegister = viewModelRegister,
            uiStateSignIn = uiStateSignIn,
            uiStateRegister = uiStateRegister,
            onSignInClick = {
                scope.launch {
                    viewModelSignIn.signIn()
                }
            },
            onCreateClick = {
                scope.launch {
                    viewModelRegister.register()
                }
            }
        )
    }
}