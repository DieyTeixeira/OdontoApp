package com.codek.loginapp.login.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.loginapp.login.presentation.ui.actions.ButtonClickAction

@Composable
fun ButtonBorder(
    textButton: String,
    colorBorder: Color,
    onClick: () -> Unit,
) {

    val buttonClickAction = remember { ButtonClickAction() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(30.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(100)
            )
            .border(
                width = 1.dp,
                color = colorBorder,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    if (buttonClickAction.offClick()) {
                        focusManager.clearFocus()
                        onClick()
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textButton,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ButtonFilled(
    textButton: String,
    colorButton: Color,
    onClick: () -> Unit,
) {

    val buttonClickAction = remember { ButtonClickAction() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(30.dp)
            .background(
                color = colorButton,
                shape = RoundedCornerShape(100)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    if (buttonClickAction.offClick()) {
                        focusManager.clearFocus()
                        onClick()
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textButton,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ButtonText(
    textButton: String,
    colorText: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(180.dp)
            .height(30.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textButton,
            color = colorText,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                textDecoration = TextDecoration.Underline
            )
        )
    }
}