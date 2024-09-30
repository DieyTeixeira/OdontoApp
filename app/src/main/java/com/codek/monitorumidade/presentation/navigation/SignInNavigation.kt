package com.codek.monitorumidade.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codek.monitorumidade.presentation.ui.screens.SignInScreen
import com.codek.monitorumidade.presentation.viewmodel.SignInViewModel
import org.koin.androidx.compose.koinViewModel

const val signInRoute: String = "signIn"

fun NavGraphBuilder.signInScreen(
    onNavigateToRegister: () -> Unit
) {
    composable(signInRoute) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        SignInScreen(
            uiState = uiState,
            onSignInClick = {},
            onSignUpClick = onNavigateToRegister
        )
    }
}

fun NavHostController.navigateToSignIn(
    navOptions: NavOptions? = null
) {
    navigate(signInRoute, navOptions)
}
