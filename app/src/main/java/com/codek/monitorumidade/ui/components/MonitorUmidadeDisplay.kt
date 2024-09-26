package com.codek.monitorumidade.ui.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codek.monitorumidade.connectivity.ConnectivityObserver
import com.codek.monitorumidade.ui.theme.Verde
import com.codek.monitorumidade.ui.theme.VerdeEscuro
import androidx.compose.runtime.livedata.observeAsState
import com.codek.monitorumidade.ui.controladores.ControladorUmidade

@Composable
fun MonitorUmidadeDisplay() {
    val context = LocalContext.current
    val connectivityObserver = remember { ConnectivityObserver(context) }
    val hasInternetConnection by connectivityObserver.observeAsState(initial = connectivityObserver.hasInternetConnection())

    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    val gradientBrush = if (hasInternetConnection) {
        Brush.sweepGradient(listOf(VerdeEscuro, Verde))
    } else {
        Brush.sweepGradient(listOf(Color.Red, Color.Red))
    }

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .height(250.dp),
        shape = RoundedCornerShape(10)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .drawWithContent {
                    if (hasInternetConnection) {
                        rotate(degrees = degrees) {
                            drawCircle(
                                brush = gradientBrush,
                                radius = size.width,
                                blendMode = BlendMode.SrcIn,
                            )
                        }
                    } else {
                        drawCircle(
                            brush = gradientBrush,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(9)
        ) {
            MonitorLayout()
        }
    }
}

@Preview
@Composable
private fun MonitorUmidadeDisplayPreview() {
    MonitorUmidadeDisplay()
}
