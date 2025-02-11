package com.codek.odontoapp.splash.loadings

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.codek.odontoapp.splash.animation.fractionTransition

@Composable
fun RotatingPlane(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1200,
    delayMillis: Int = 0,
    size: DpSize = DpSize(40.dp, 40.dp),
    color: Color,
    shape: Shape = RectangleShape,
    contentOnPlane: @Composable BoxScope. () -> Unit = {}
) {
    val transition = rememberInfiniteTransition()

    val rotationYValue = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 180f,
        durationMillis = durationMillis / 2,
        delayMillis = durationMillis / 2 + delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    val rotationXValue = transition.fractionTransition(
        initialValue = 0f,
        targetValue = -180f,
        durationMillis = durationMillis / 2,
        delayMillis = durationMillis / 2 + delayMillis,
        offsetMillis = durationMillis / 2 + delayMillis,
        repeatMode = RepeatMode.Restart,
        easing = EaseInOut
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier
                .size(size)
                .graphicsLayer {
                    rotationY = rotationYValue.value
                    rotationX = rotationXValue.value
                },
            color = color,
            shape = shape
        ) {
            contentOnPlane()
        }
    }
}