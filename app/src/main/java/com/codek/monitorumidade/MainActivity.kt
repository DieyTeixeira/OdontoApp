package com.codek.monitorumidade

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.codek.monitorumidade.presentation.navigation.appAgroScreen
import com.codek.monitorumidade.presentation.navigation.authGraph
import com.codek.monitorumidade.presentation.navigation.navigateToRegister
import com.codek.monitorumidade.presentation.navigation.navigateToSignIn
import com.codek.monitorumidade.presentation.navigation.signInScreen
import com.codek.monitorumidade.presentation.navigation.splashScreen
import com.codek.monitorumidade.presentation.navigation.splashScreenRoute
import com.codek.monitorumidade.presentation.ui.theme.MonitorUmidadeTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonitorUmidadeTheme {

                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    delay(10000)
                    navController.navigateToSignIn()
                }

                NavHost(
                    navController = navController,
                    startDestination = splashScreenRoute
                ) {
                    splashScreen()
                    authGraph(
                        onNavigateToSignIn = {
                            navController.navigateToSignIn(it)
                        },
                        onNavigateToRegister = {
                            navController.navigateToRegister()
                        },
                        onPopBackStack = { navController.popBackStack() }
                    )
                    signInScreen(
                        onNavigateToRegister = { navController.navigateToRegister() }
                    )
                    appAgroScreen()
                }
            }
        }
    }
}