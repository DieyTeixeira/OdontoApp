package com.codek.monitorumidade.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.codek.monitorumidade.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_screen_animation))
    var isPlaying by remember { mutableStateOf(true) }
    var loadingMessage by remember { mutableStateOf("Carregando...") }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = Int.MAX_VALUE
    )

    LaunchedEffect(Unit) {
        delay(2000) // Simula a verificação da versão do app
        loadingMessage = "Verificando versão do app"
        delay(2000) // Simula a autenticação do usuário
        loadingMessage = "Carregamento completo"
        delay(1000) // Aguarda um pouco antes de mudar de tela
        // Navegue para a próxima tela aqui
        // Exemplo: navController.navigate("home")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF000000)),
        contentAlignment = Alignment.Center
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LottieAnimation(
                modifier = Modifier.size(300.dp),
                composition = composition,
                progress = { progress }
            )
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