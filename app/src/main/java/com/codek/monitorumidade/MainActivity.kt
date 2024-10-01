package com.codek.monitorumidade

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.codek.monitorumidade.presentation.navigation.appAgroScreen
import com.codek.monitorumidade.presentation.navigation.registerScreen
import com.codek.monitorumidade.presentation.navigation.signInScreen
import com.codek.monitorumidade.presentation.navigation.splashScreen
import com.codek.monitorumidade.presentation.ui.theme.MonitorUmidadeTheme
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("signIn")
    object AppAgro : Screen("appAgro")
    object Register : Screen("register")
}

fun NavHostController.navigateToScreen(route: String) {
    navigate(route)
}

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
                    navController.navigateToScreen("signIn")
                }

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    splashScreen()
                    appAgroScreen()
                    signInScreen(
                        onRegisterClick = { navController.navigateToScreen("register") },
                        onLoginSuccess = { navController.navigateToScreen("appAgro") }
                    )
                    registerScreen(
                        onCreateClick = { navController.navigateToScreen("signIn") }
                    )
                }
            }
        }
    }
}