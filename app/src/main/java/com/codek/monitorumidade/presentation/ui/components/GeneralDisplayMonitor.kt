package com.codek.monitorumidade.presentation.ui.components

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
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
import com.codek.monitorumidade.presentation.ui.theme.Green400
import com.codek.monitorumidade.presentation.ui.theme.Green700
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import com.codek.monitorumidade.presentation.ui.components.controladores.BrightnessController
import com.codek.monitorumidade.presentation.ui.components.controladores.TemperatureController
import com.codek.monitorumidade.presentation.ui.components.controladores.HumidityController
import com.codek.monitorumidade.presentation.ui.theme.DarkGradient
import org.koin.android.ext.koin.androidContext

@Composable
fun GeneralDisplayMonitor() {
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
        Brush.sweepGradient(listOf(Green700, Green400))
    } else {
        Brush.sweepGradient(listOf(Color.Red, Color.Red))
    }

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .height(500.dp),
        shape = RoundedCornerShape(9)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
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
            shape = RoundedCornerShape(8)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGradient)
            ) {
                MonitorLayout()
            }
        }
    }
}

@Composable
fun MonitorLayout() {

    Column(
        modifier = Modifier
            .size(500.dp)
            .padding(5.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier.weight(1f),
        ) {

            Box(
                modifier = Modifier.weight(3f)
            ) {
                HumidityController()
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 8.dp),
                color = Color.LightGray
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TemperatureController()
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(bottom = 8.dp),
                    color = Color.LightGray
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BrightnessController()
                }

            }
        }
    }
}

@Preview
@Composable
private fun GeneralDisplayMonitorPreview() {
    GeneralDisplayMonitor()
}
