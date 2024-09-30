package com.codek.monitorumidade.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.codek.monitorumidade.presentation.ui.screens.AppAgroScreen

const val appAgroRoute = "appAgro"

fun NavGraphBuilder.appAgroScreen() {
    composable(appAgroRoute) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AppAgroScreen()
        }
    }
}

fun NavHostController.navigateToAppAgro() {
    navigate(appAgroRoute)
}