package com.codek.odontoapp

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
import com.codek.odontoapp.aplication.presentation.navigation.telaScreen
import com.codek.odontoapp.login.presentation.navigation.loginScreen
import com.codek.odontoapp.splash.navigation.splashScreen
import com.codek.odontoapp.adm_app.theme.odontoappTheme
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Tela : Screen("tela")
    object Login : Screen("login")
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
            odontoappTheme {

                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    delay(10000)
                    val isLoggedIn = preferences.getBoolean("isLoggedIn", false)

                    if (isLoggedIn) {
                        navController.navigateToScreen("tela", "splash")
                    } else {
                        navController.navigateToScreen("login", "splash")
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    splashScreen()
                    loginScreen(
                        onLoginSuccess = { navController.navigateToScreen("tela", "login") }
                    )
                    telaScreen(
                        onSignOut = { navController.navigateToScreen("login", "tela") }
                    )
                }
            }
        }
    }
}