package com.codek.monitorumidade.ui.controladores

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.R
import com.codek.monitorumidade.ui.states.UiStateIndicator
import com.codek.monitorumidade.ui.theme.Green500
import com.codek.monitorumidade.ui.theme.GreenGrade
import com.codek.monitorumidade.ui.theme.GreenGrade2
import com.codek.monitorumidade.ui.theme.GreenGrade3
import com.codek.monitorumidade.ui.theme.OrangeGrade
import com.codek.monitorumidade.ui.theme.RedGrade

@Composable
fun SpeedTestScreen(state: UiStateIndicator) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        SpeedIndicator(state = state)
    }
}

@Composable
fun SpeedIndicator(state: UiStateIndicator) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        CircularSpeedIndicator(state.arcValue, 240f)
        SpeedValue(state.speed)
    }
}

@Composable
fun SpeedValue(value: String) {
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
            text = "48%",
            fontSize = 40.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = "UMIDADE DO SOLO",
            fontSize = 14.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CircularSpeedIndicator(value: Float, angle: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        drawArcs(value, angle)
    }
}

fun DrawScope.drawArcs(progress: Float, maxValue: Float) {
    val startAngle = 270 - maxValue / 2
    val sweepAngle = maxValue * progress

    val topLeft = Offset(50f, 80f)
    val size = Size(size.width - 100f, size.height - 100f)

    val sweepGradient = Brush.sweepGradient(
        colors = listOf(
            RedGrade, // 0°
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
            RedGrade, // 180°
            OrangeGrade, // 195°
            GreenGrade, // 210°
            GreenGrade2, // 225°
            GreenGrade3, // 240°
            GreenGrade3, // 255°
            GreenGrade3, // 270°
            GreenGrade3, // 285°
            GreenGrade3, // 300°
            GreenGrade2, // 315°
            GreenGrade, // 330°
            OrangeGrade, // 345°
            RedGrade, // 360°
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

//    fun drawStroke() {
//        drawArc(
//            color = Blue500,
//            startAngle = startAngle,
//            sweepAngle = sweepAngle,
//            useCenter = false,
//            topLeft = topLeft,
//            size = size,
//            style = Stroke(width = 86f, cap = StrokeCap.Round)
//        )
//    }

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
//    drawStroke()
    drawGradient()
}

@Preview
@Composable
private fun SpeedIndicatorPreview() {
    SpeedIndicator(
        UiStateIndicator(
            speed = "48%",
            maxHumidity = "48%",
            arcValue = 0.48f,
            inProgress = false
        )
    )
}