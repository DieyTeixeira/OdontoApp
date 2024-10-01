package com.codek.monitorumidade.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.R
import com.codek.monitorumidade.presentation.states.SignInUiState
import com.codek.monitorumidade.presentation.ui.actions.ButtonClickAction
import com.codek.monitorumidade.presentation.ui.actions.vibrateAction
import com.codek.monitorumidade.presentation.ui.components.Baseboard
import com.codek.monitorumidade.presentation.ui.theme.DarkGradient
import com.codek.monitorumidade.presentation.ui.theme.RedGrade

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun SignInScreen(
    color: Color = Color.White,
    uiState: SignInUiState,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val context = LocalContext.current
    val buttonClickAction = remember { ButtonClickAction() }
    val focusManager = LocalFocusManager.current

    val isError = uiState.error != null

    LaunchedEffect(isError) {
        if (isError) {
            vibrateAction(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        /***** MENSAGEM DE ERRO *****/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            MensagemErro(isError, uiState)
        }

        Spacer(modifier = Modifier.height(20.dp))

        /***** LOGO *****/
        Image(
            painter = painterResource(id = R.drawable.logocodek_allwhite),
            contentDescription = "Logo Codek",
            modifier = Modifier
                .size(130.dp)
        )

        /***** CAMPO USUÁRIO *****/
        OutlinedTextField(
            value = uiState.email,
            onValueChange = uiState.onEmailChange,
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = color
            ),
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "ícone de usuário",
                    tint = color
                )
            },
            label = {
                Text(
                    text = "Email",
                    color = Color.LightGray
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                unfocusedBorderColor = color
            )
        )

        /***** CAMPO SENHA *****/
        OutlinedTextField(
            value = uiState.password,
            onValueChange = uiState.onPasswordChange,
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = color
            ),
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    Icons.Filled.Password,
                    contentDescription = "ícone de senha",
                    tint = color
                )
            },
            label = {
                Text(
                    text = "Senha",
                    color = Color.LightGray
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                unfocusedBorderColor = color
            ),
            trailingIcon = {
                val trailingIconModifier = Modifier.clickable {
                    uiState.onTogglePasswordVisibility()
                }
                if (uiState.isShowPassword) {
                    Icon(
                        Icons.Filled.Visibility,
                        contentDescription = "ícone de visível",
                        modifier = trailingIconModifier,
                        tint = color
                    )
                } else {
                    Icon(
                        Icons.Filled.VisibilityOff,
                        contentDescription = "ícone de não visível",
                        modifier = trailingIconModifier,
                        tint = color
                    )
                }
            },
            visualTransformation = if (uiState.isShowPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = uiState.passwordCharError != null
        )
        if (uiState.passwordCharError != null) {
            Text(
                text = uiState.passwordCharError,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        /***** BOTÕES *****/
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(35.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(100)
                )
                .padding(8.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        if (buttonClickAction.offClick()) {
                            focusManager.clearFocus()
                            onSignInClick()
                        }
                    }
                )
        ) {
            Text(
                text = "Entrar",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(35.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(100)
                )
                .padding(8.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        if (buttonClickAction.offClick()) {
                            focusManager.clearFocus()
                            onSignUpClick()
                        }
                    }
                )
        ) {
            Text(
                text = "Cadastrar Usuário",
                fontSize = 16.sp,
                color = color,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        /***** RODAPÉ *****/
        Baseboard(color = Color.LightGray)
    }
}

@Composable
private fun MensagemErro(
    isError: Boolean,
    uiState: SignInUiState,
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
            val error = uiState.error ?: ""
            Text(
                text = error,
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        color = Color.White,
        uiState = SignInUiState(),
        onSignInClick = {},
        onSignUpClick = {}
    )
}