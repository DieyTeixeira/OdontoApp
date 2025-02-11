package com.codek.odontoapp.splash.loadings

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.codek.odontoapp.splash.animation.fractionTransition

@Composable
fun DoubleBounce(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000,
    delayMillis: Int = 0,
    size: DpSize = DpSize(40.dp, 40.dp),
    color: Color,
    shape: Shape = CircleShape
) {
    val transition = rememberInfiniteTransition()

    val sizeMultiplier1 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 1f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    val alphaMultiplier1 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0.5f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    val sizeMultiplier2 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 1f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        offsetMillis = durationMillis / 2,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    val alphaMultiplier2 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0.5f,
        durationMillis = durationMillis / 2,
        delayMillis = delayMillis,
        offsetMillis = durationMillis / 2,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier.size(size * sizeMultiplier1.value),
            shape = shape,
            color = color.copy(alphaMultiplier1.value)
        ) {}
        Surface(
            modifier = Modifier.size(size * sizeMultiplier2.value),
            shape = shape,
            color = color.copy(alphaMultiplier2.value)
        ) {}
    }
}