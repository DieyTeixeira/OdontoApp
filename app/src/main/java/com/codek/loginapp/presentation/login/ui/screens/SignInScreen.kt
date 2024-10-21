package com.codek.loginapp.login.presentation.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.loginapp.login.presentation.states.SignInUiState
import com.codek.loginapp.presentation.login.ui.actions.ButtonClickAction
import com.codek.loginapp.login.presentation.ui.actions.vibrateAction
import com.codek.loginapp.login.presentation.ui.components.ButtonBorder
import com.codek.loginapp.login.presentation.ui.components.ButtonFilled
import com.codek.loginapp.login.presentation.ui.components.ButtonText
import com.codek.loginapp.login.presentation.ui.components.MensagemErro
import com.codek.loginapp.login.presentation.ui.theme.LoginPri
import com.codek.loginapp.login.presentation.viewmodel.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    uiState: SignInUiState,
    onSignInClick: () -> Unit,
    onGoRegisterClick: () -> Unit
) {

    val context = LocalContext.current
    val isError = uiState.error != null
    val uiStateError = uiState.error ?: ""

    LaunchedEffect(isError) {
        if (isError) {
            vibrateAction(context)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                /***** MENSAGEM DE ERRO *****/
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                ) {
                    MensagemErro(isError, uiStateError)
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Acesse Seu Painel de Agendamentos",
                    color = LoginPri,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                /***** CAMPO USUÁRIO *****/
                TextField(
                    value = uiState.email,
                    onValueChange = uiState.onEmailChange,
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        lineHeight = 8.sp,
                        color = Color.Gray
                    ),
                    shape = RoundedCornerShape(10.dp),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Email,
                            contentDescription = "ícone de email",
                            tint = Color.Gray
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Email",
                            fontSize = 16.sp,
                            lineHeight = 8.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(48.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledIndicatorColor = Color.Gray,
                        errorIndicatorColor = Color.Red
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                /***** CAMPO SENHA *****/
                TextField(
                    value = uiState.password,
                    onValueChange = uiState.onPasswordChange,
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        lineHeight = 8.sp,
                        color = Color.Gray
                    ),
                    shape = RoundedCornerShape(10.dp),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = "ícone de senha",
                            tint = Color.Gray
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Senha",
                            fontSize = 16.sp,
                            lineHeight = 8.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(48.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledIndicatorColor = Color.Gray,
                        errorIndicatorColor = Color.Red
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
                                tint = Color.Gray
                            )
                        } else {
                            Icon(
                                Icons.Filled.VisibilityOff,
                                contentDescription = "ícone de não visível",
                                modifier = trailingIconModifier,
                                tint = Color.Gray
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
                ButtonText(
                    textButton = "Esqueceu sua senha?",
                    colorText = Color.White,
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(35.dp))
                ButtonFilled(
                    textButton = "ENTRAR",
                    colorButton = LoginPri,
                    onClick = onSignInClick
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Olá, Amigo!",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Crie uma nova conta e comece a organizar" +
                            "\nseus agendamentos com facilidade.",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                ButtonBorder(
                    textButton = "INSCREVER-SE",
                    colorBorder = Color.White,
                    onClick = {
                        onGoRegisterClick()
                        viewModel.clearFields()
                    }
                )
            }
        }
    }
}