package com.codek.monitorumidade.presentation.ui.components.controladores

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.R
import com.codek.monitorumidade.data.api.AgroApi
import com.codek.monitorumidade.data.api.ApiCreateAgro
import com.codek.monitorumidade.data.repository.AgroRepositoryImpl
import com.codek.monitorumidade.presentation.ui.theme.GreenGrade1
import com.codek.monitorumidade.presentation.ui.theme.GreenGrade2
import com.codek.monitorumidade.presentation.ui.theme.GreenGrade3
import com.codek.monitorumidade.presentation.ui.theme.OrangeGrade
import com.codek.monitorumidade.presentation.ui.theme.RedGrade
import com.codek.monitorumidade.presentation.ui.theme.YellowGrade1
import com.codek.monitorumidade.presentation.ui.theme.YellowGrade2
import com.codek.monitorumidade.ui.viewmodels.AppAgroViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HumidityController() {

    val preferences = LocalContext.current.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val apiCreateAgro = ApiCreateAgro.createAgro(AgroApi::class.java)
        val repositoryAgro = AgroRepositoryImpl(apiCreateAgro)
        val monitorViewModel = AppAgroViewModel(repositoryAgro, preferences)

        HumidityIndicator(
            monitorViewModel = monitorViewModel
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun HumidityIndicator(
    monitorViewModel: AppAgroViewModel
) {

    val humidityValue by monitorViewModel.humidityValue.collectAsState()
    Log.d("HumidityIndicator", "humidityValue: $humidityValue")
    var consulta by remember { mutableStateOf("") }
    var previousHumidity by remember { mutableStateOf(humidityValue ?: 1f) }

    LaunchedEffect(Unit) {
        while (true) {
            monitorViewModel.loadAgroData()
            Log.d("HumidityIndicator", "consulta")
            Log.d("HumidityIndicator", "humidityValue: $humidityValue")

            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            consulta = currentDateTime.format(formatter)

            delay(30000)
        }
    }

    val animatedHumidity by animateFloatAsState(
        targetValue = humidityValue ?: 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = { fraction -> 1 - (1 - fraction) * (1 - fraction) }
        )
    )

    if (humidityValue != previousHumidity) {
        previousHumidity = humidityValue ?: 0f
        Log.d("HumidityIndicator", "previousHumidity: $previousHumidity")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            CircularIndicator(value = animatedHumidity / 100f)
            IndicatorValue(value = "${humidityValue ?: 0f}", consultaValue = consulta)
        }
    }
}

@Composable
fun IndicatorValue(value: String, consultaValue: String) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.humidity2),
                contentDescription = "Umidade do solo",
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "$value%",
            fontSize = 40.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "UMIDADE DO SOLO",
            fontSize = 18.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Última consulta: " + consultaValue,
            fontSize = 12.sp,
            color = Color.LightGray,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun CircularIndicator(value: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        drawArcs(value, 240f)
    }
}

fun DrawScope.drawArcs(progress: Float, maxValue: Float) {
    val startAngle = 270 - maxValue / 2
    val sweepAngle = maxValue * progress

    val topLeft = Offset(50f, 80f)
    val size = Size(size.width - 100f, size.height - 100f)

    val sweepGradient = Brush.sweepGradient(
        colors = listOf(
            OrangeGrade, // 0°
            RedGrade, // 15°
            RedGrade, // 30°
            RedGrade, // 45°
            RedGrade, // 60°
            RedGrade, // 75°
            RedGrade, // 90°
            RedGrade, // 105°
            RedGrade, // 120°
            RedGrade, // 135°
            RedGrade, // 150°
            RedGrade, // 165°
            OrangeGrade, // 180°
            YellowGrade2, // 195°
            YellowGrade1, // 210°
            GreenGrade1, // 225°
            GreenGrade2, // 240°
            GreenGrade3, // 255°
            GreenGrade3, // 270°
            GreenGrade3, // 285°
            GreenGrade2, // 300°
            GreenGrade1, // 315°
            YellowGrade1, // 330°
            YellowGrade2, // 345°
            OrangeGrade, // 360°
        ),
        center = Offset(size.width / 2, size.height / 2) // Define o centro para a varredura do gradiente
    )

    fun drawBlur() {
        for (i in 0..20) {
            drawArc(
                color = Color.LightGray.copy(alpha = i / 900f),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = 20f + (18 - i) * 15, cap = StrokeCap.Round)
            )
        }
    }

    fun drawGradient() {
        drawArc(
            brush = sweepGradient,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = 80f, cap = StrokeCap.Round)
        )
    }

    drawBlur()
    drawGradient()
}