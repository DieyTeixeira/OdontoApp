package com.codek.odontoapp.login.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.odontoapp.Screen
import com.codek.odontoapp.splash.animation.enterTransition
import com.codek.odontoapp.splash.animation.exitTransition
import com.codek.odontoapp.splash.animation.popEnterTransition
import com.codek.odontoapp.splash.animation.popExitTransition
import com.codek.odontoapp.login.presentation.ui.screens.LoginScreen
import com.codek.odontoapp.login.presentation.viewmodel.RegisterViewModel
import com.codek.odontoapp.login.presentation.viewmodel.SignInViewModel
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