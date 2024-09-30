package com.codek.monitorumidade.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation

const val authGraphRoute = "authGraph"

@SuppressLint("NewApi")
fun NavGraphBuilder.authGraph(
    onNavigateToRegister: () -> Unit,
    onNavigateToSignIn: (NavOptions) -> Unit,
    onPopBackStack: () -> Unit
) {
    navigation(
        route = authGraphRoute,
        startDestination = signInRoute
    ) {
        signInScreen(
            onNavigateToRegister = onNavigateToRegister
        )
        registerScreen(
            onNavigationToSignIn = {
                onNavigateToSignIn(navOptions {
                    popUpTo(authGraphRoute)
                })
            },
            onPopBackStack = onPopBackStack
        )
    }
}

fun NavHostController.navigateToAuthGraph(
    navOptions: NavOptions? = navOptions {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
) {
    navigate(authGraphRoute, navOptions)
}