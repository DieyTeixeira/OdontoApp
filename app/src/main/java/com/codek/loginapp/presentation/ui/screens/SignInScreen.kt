package com.codek.loginapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.loginapp.R
import com.codek.loginapp.presentation.states.SignInUiState
import com.codek.loginapp.presentation.ui.actions.ButtonClickAction
import com.codek.loginapp.presentation.ui.components.FooterBar
import com.codek.loginapp.presentation.ui.components.MensagemErro
import com.codek.loginapp.presentation.ui.theme.LoginBack
import com.codek.loginapp.presentation.ui.theme.LoginPri
import com.codek.loginapp.presentation.ui.theme.LoginSec
import com.codek.loginapp.presentation.viewmodel.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    color: Color = Color.White,
    uiState: SignInUiState,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val buttonClickAction = remember { ButtonClickAction() }
    val focusManager = LocalFocusManager.current

    val isError = uiState.error != null
    val uiStateError = uiState.error ?: ""

//    LaunchedEffect(isError) {
//        if (isError) {
//            vibrateAction(context)
//        }
//    }

    LaunchedEffect(Unit) {
        viewModel.checkSavedCredentials()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBack),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Box {
            Image(
                painter = painterResource(id = R.drawable.logo_odonto),
                contentDescription = "logo",
                modifier = Modifier
                    .height(45.dp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(420.dp)
                .background(
                    color = LoginSec,
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
                        .height(45.dp),
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
                        .height(45.dp),
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
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(30.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { /*BOTÃO DE ESQUECEU A SENHA*/ }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Esqueceu sua senha?",
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
                Spacer(modifier = Modifier.height(35.dp))
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(30.dp)
                        .background(
                            color = LoginPri,
                            shape = RoundedCornerShape(100)
                        )
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                if (buttonClickAction.offClick()) {
                                    focusManager.clearFocus()
                                    onSignInClick()
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ENTRAR",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(200.dp)
                .background(
                    color = LoginPri,
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
                Spacer(modifier = Modifier.height(25.dp))
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
                            color = Color.White,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                if (buttonClickAction.offClick()) {
                                    focusManager.clearFocus()
                                    onSignUpClick()
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "INSCREVER-SE",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        /***** RODAPÉ *****/
        FooterBar(color = Color.LightGray)
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        viewModel = viewModel(),
        color = Color.White,
        uiState = SignInUiState(),
        onSignInClick = {},
        onSignUpClick = {}
    )
}