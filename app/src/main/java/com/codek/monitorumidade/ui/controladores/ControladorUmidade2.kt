package com.codek.monitorumidade.ui.controladores

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.R
import com.codek.monitorumidade.ui.components.indicator.VerticalDivider
import com.codek.monitorumidade.ui.components.indicator.drawArcs
import com.codek.monitorumidade.ui.components.indicator.drawLines
import com.codek.monitorumidade.ui.states.UiStateIndicator
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

suspend fun startHumidityAnimation(animation: Animatable<Float, AnimationVector1D>) {
    animation.animateTo(0.53f, keyframes {
        durationMillis = 5000 // Anima até 53% em 5 segundos
        0f at 0 with CubicBezierEasing(0f, 1.5f, 0.8f, 1f)
    })
}

fun Animatable<Float, AnimationVector1D>.toUiStateHumidity() = UiStateIndicator(
    arcValue = value,
    speed = "%.0f".format(value * 100),  // Aqui o valor é uma porcentagem
    inProgress = isRunning
)

@Composable
fun HumidityMonitorScreen() {
    val coroutineScope = rememberCoroutineScope()

    val animation = remember { Animatable(0f) }
    val maxHumidity = remember { mutableStateOf(0f) }
    maxHumidity.value = maxHumidity.value.coerceAtLeast(animation.value * 100f)

    HumidityMonitorScreenContent(animation.toUiStateHumidity()) {
        coroutineScope.launch {
            maxHumidity.value = 0f
            startHumidityAnimation(animation)
        }
    }
}

@Composable
private fun HumidityMonitorScreenContent(state: UiStateIndicator, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        HumidityIndicator(state = state)
    }
}

@Composable
fun HumidityIndicator(state: UiStateIndicator) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        CircularIndicator(state.arcValue, 240f) // Reutiliza o gráfico circular
        HumidityValue(state.speed)
    }
}

@Composable
fun CircularIndicator(value: Float, angle: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        drawLines(value, angle)
        drawArcs(value, angle)
    }
}

@Composable
fun HumidityValue(value: String) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("UMIDADE")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Umidade do solo",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = value + "%",
            fontSize = 45.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun HumidityMonitorScreenPreview() {
    HumidityMonitorScreenContent(
        UiStateIndicator(
            speed = "53",
            ping = "53%",
            maxSpeed = "-",
            arcValue = 0.53f,
            inProgress = false
        )
    ) { }
}