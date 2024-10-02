package com.codek.monitorumidade

import android.annotation.SuppressLint
import android.content.Context
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

fun NavHostController.navigateToScreen(route: String, popUpToRoute: String? = null) {
    this.navigate(route) {
        popUpToRoute?.let {
            popUpTo(it) { inclusive = true }
        }
    }
}

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        setContent {
            MonitorUmidadeTheme {

                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    delay(10000)
                    val email = preferences.getString("email", null)
                    val password = preferences.getString("password", null)

                    if (email != null && password != null) {
                        navController.navigateToScreen("appAgro", "splash")
                    } else {
                        navController.navigateToScreen("signIn", "splash")
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    splashScreen()
                    appAgroScreen(
                        onSignOutClick = { navController.navigateToScreen("signIn", "appAgro") }
                    )
                    signInScreen(
                        onRegisterClick = { navController.navigateToScreen("register") },
                        onLoginSuccess = { navController.navigateToScreen("appAgro", "signIn") }
                    )
                    registerScreen(
                        onCreateClick = { navController.navigateToScreen("signIn", "register") },
                        onBackClick = { navController.navigateToScreen("signIn", "register") }
                    )
                }
            }
        }
    }
}