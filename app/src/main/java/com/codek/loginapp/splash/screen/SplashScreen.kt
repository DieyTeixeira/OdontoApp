package com.codek.loginapp.splash.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codek.loginapp.splash.loadings.ChasingTwoDots
import com.codek.loginapp.adm_app.theme.LoginPri
import com.codek.loginapp.adm_app.theme.LoginSec
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {

    var loadingMessage by remember { mutableStateOf("Carregando...") }

    LaunchedEffect(Unit) {
        delay(5000)
        loadingMessage = "Verificando versão do app"
        delay(5000)
        loadingMessage = "Carregamento completo"
        delay(5000)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LoginSec),
        contentAlignment = Alignment.Center
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ChasingTwoDots(color = LoginPri)
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = loadingMessage, color = Color.White)
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}