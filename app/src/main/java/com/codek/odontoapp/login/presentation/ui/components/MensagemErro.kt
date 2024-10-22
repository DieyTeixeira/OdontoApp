package com.codek.odontoapp.login.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.odontoapp.adm_app.theme.LoginError

@Composable
fun MensagemErro(
    isError: Boolean,
    uiStateError: String,
) {
    AnimatedVisibility(
        visible = isError,
        enter = fadeIn(animationSpec = spring(dampingRatio = 0.5f, stiffness = 200f)) + scaleIn(
            initialScale = 0.3f,
            animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f)
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 400)) + scaleOut(
            targetScale = 0.3f,
            animationSpec = spring(dampingRatio = 0.8f, stiffness = 500f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            LoginError.copy(alpha = 0.0f),
                            LoginError.copy(alpha = 0.7f),
                            LoginError,
                            LoginError,
                            LoginError,
                            LoginError.copy(alpha = 0.7f),
                            LoginError.copy(alpha = 0.0f)
                        )
                    )
                )
        ) {
            Text(
                text = uiStateError,
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp)
            )
        }
    }
}