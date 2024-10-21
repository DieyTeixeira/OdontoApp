package com.codek.loginapp.login.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
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
import com.codek.loginapp.login.presentation.ui.components.loadings.ChasingDots
import com.codek.loginapp.login.presentation.ui.components.loadings.ChasingTwoDots
import com.codek.loginapp.login.presentation.ui.components.loadings.Circle
import com.codek.loginapp.login.presentation.ui.components.loadings.CubeGrid
import com.codek.loginapp.login.presentation.ui.components.loadings.DoubleBounce
import com.codek.loginapp.login.presentation.ui.components.loadings.FadingCircle
import com.codek.loginapp.login.presentation.ui.components.loadings.FoldingCube
import com.codek.loginapp.login.presentation.ui.components.loadings.InstaSpinner
import com.codek.loginapp.login.presentation.ui.components.loadings.Pulse
import com.codek.loginapp.login.presentation.ui.components.loadings.RotatingPlane
import com.codek.loginapp.login.presentation.ui.components.loadings.ThreeBounce
import com.codek.loginapp.login.presentation.ui.components.loadings.WanderingCubes
import com.codek.loginapp.login.presentation.ui.components.loadings.Wave
import com.codek.loginapp.login.presentation.ui.theme.LoginPri
import com.codek.loginapp.login.presentation.ui.theme.LoginSec
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