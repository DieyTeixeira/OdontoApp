package com.codek.monitorumidade.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.monitorumidade.Screen
import com.codek.monitorumidade.presentation.ui.animation.enterTransition
import com.codek.monitorumidade.presentation.ui.animation.exitTransition
import com.codek.monitorumidade.presentation.ui.animation.popEnterTransition
import com.codek.monitorumidade.presentation.ui.animation.popExitTransition
import com.codek.monitorumidade.presentation.ui.screens.AppAgroScreen
import com.codek.monitorumidade.presentation.viewmodel.SignOutViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.appAgroScreen(
    onSignOutClick: () -> Unit
) {
    composable(
        route = Screen.AppAgro.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        val viewModel = koinViewModel<SignOutViewModel>()
        val scope = rememberCoroutineScope()

        LaunchedEffect(viewModel.signInIsSucessful) {
            viewModel.signInIsSucessful.collect { success ->
                if (success == false) {
                    onSignOutClick()
                }
            }
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            AppAgroScreen(
                onSignOutClick = {
                    scope.launch {
                        viewModel.signOut()
                    }
                }
            )
        }
    }
}
