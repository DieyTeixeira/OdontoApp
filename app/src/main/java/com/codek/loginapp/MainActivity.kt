package com.codek.loginapp

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
import com.codek.loginapp.presentation.navigation.registerScreen
import com.codek.loginapp.presentation.navigation.signInScreen
import com.codek.loginapp.presentation.navigation.splashScreen
import com.codek.loginapp.presentation.navigation.telaScreen
import com.codek.loginapp.presentation.ui.theme.LoginAppTheme
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("signIn")
    object Register : Screen("register")
    object Tela : Screen("tela")
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
            LoginAppTheme {

                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    delay(10000)
                    val isLoggedIn = preferences.getBoolean("isLoggedIn", false)

                    if (isLoggedIn) {
                        navController.navigateToScreen("tela", "splash")
                    } else {
                        navController.navigateToScreen("signIn", "splash")
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    splashScreen()
                    registerScreen(
                        onBackClick = { navController.navigateToScreen("signIn", "register") }
                    )
                    signInScreen(
                        onRegisterClick = { navController.navigateToScreen("register") },
                        onLoginSuccess = { navController.navigateToScreen("tela", "signIn") }
                    )
                    telaScreen(
                        onSignOut = { navController.navigateToScreen("signIn", "tela") }
                    )
                }
            }
        }
    }
}