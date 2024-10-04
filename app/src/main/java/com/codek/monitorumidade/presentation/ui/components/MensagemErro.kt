package com.codek.monitorumidade.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.codek.monitorumidade.presentation.states.RegisterUiState
import com.codek.monitorumidade.presentation.states.SignInUiState
import com.codek.monitorumidade.presentation.ui.theme.RedGrade

@Composable
fun MensagemErro(
    isError: Boolean,
    uiStateError: String,
) {
    AnimatedVisibility(
        visible = isError,
        enter = slideInVertically(
            initialOffsetY = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 400)
        ) + fadeIn(animationSpec = tween(durationMillis = 400)),
        exit = slideOutVertically(
            targetOffsetY = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 400)
        ) + fadeOut(animationSpec = tween(durationMillis = 400))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            RedGrade.copy(alpha = 0.0f),
                            RedGrade.copy(alpha = 0.7f),
                            RedGrade,
                            RedGrade,
                            RedGrade.copy(alpha = 0.7f),
                            RedGrade.copy(alpha = 0.0f)
                        )
                    )
                )
        ) {
            Text(
                text = uiStateError,
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 16.sp,
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