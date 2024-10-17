package com.codek.loginapp.presentation.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.loginapp.presentation.states.RegisterUiState
import com.codek.loginapp.presentation.ui.actions.ButtonClickAction
import com.codek.loginapp.presentation.ui.actions.vibrateAction
import com.codek.loginapp.presentation.ui.components.ButtonBorder
import com.codek.loginapp.presentation.ui.components.ButtonFilled
import com.codek.loginapp.presentation.ui.components.FooterBar
import com.codek.loginapp.presentation.ui.components.JanelaDialogo
import com.codek.loginapp.presentation.ui.components.MensagemErro
import com.codek.loginapp.presentation.ui.components.TextWithIcon
import com.codek.loginapp.presentation.ui.components.containsDigit
import com.codek.loginapp.presentation.ui.components.containsLowerCase
import com.codek.loginapp.presentation.ui.components.containsSpecialCharacter
import com.codek.loginapp.presentation.ui.components.containsUpperCase
import com.codek.loginapp.presentation.ui.theme.LoginError
import com.codek.loginapp.presentation.ui.theme.LoginPri
import com.codek.loginapp.presentation.ui.theme.LoginSucess
import com.codek.loginapp.presentation.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    uiState: RegisterUiState,
    onCreateClick: () -> Unit,
    onBackSignInClick: () -> Unit
) {

    val context = LocalContext.current
    val password = uiState.password
    val showDialog = remember { mutableStateOf(false) }
    val preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    val isPasswordValid = password.length >= 8 &&
            containsUpperCase(password) &&
            containsLowerCase(password) &&
            containsDigit(password) &&
            containsSpecialCharacter(password)

    val isError = uiState.error != null
    val uiStateError = uiState.error ?: ""

    LaunchedEffect(isError) {
        if (isError) {
            vibrateAction(context)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.showSaveCredentialsDialog.collect { insertValue ->
            showDialog.value = insertValue
        }
    }

    if (showDialog.value) {
        JanelaDialogo(showDialog, uiState, preferences, onBackSignInClick)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Bem-Vindo!",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Faça login para acessar seus agendamentos" +
                            "\ne gerenciar seus compromissos de forma eficiente.",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                ButtonBorder(
                    textButton = "ENTRAR",
                    colorBorder = Color.White,
                    onClick = onBackSignInClick
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
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
                    Log.d("RegisterScreen", "isError: $isError")
                    Log.d("RegisterScreen", "uiStateError: $uiStateError")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Criar Conta",
                    color = LoginPri,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                /***** CAMPO NOME *****/
                TextField(
                    value = uiState.nome,
                    onValueChange = uiState.onNameChange,
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        lineHeight = 8.sp,
                        color = Color.Gray
                    ),
                    shape = RoundedCornerShape(10.dp),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "ícone de usuário",
                            tint = Color.Gray
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Nome",
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
                /***** CAMPO EMAIL *****/
                TextField(
                    value = uiState.email,
                    onValueChange = uiState.onEmailChange,
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        lineHeight = 10.sp,
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
                            text = "E-mail",
                            fontSize = 16.sp,
                            lineHeight = 10.sp,
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
                        lineHeight = 10.sp,
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
                            lineHeight = 10.sp,
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
                                Icons.Outlined.Visibility,
                                contentDescription = "ícone de visível",
                                modifier = trailingIconModifier,
                                tint = Color.Gray
                            )
                        } else {
                            Icon(
                                Icons.Outlined.VisibilityOff,
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
                        text = uiState.passwordCharError!!,
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextWithIcon(
                            textValue = "Sua senha deve conter no mínimo 8 caracteres",
                            iconName = if (password.length >= 8) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                            iconColor = if (password.length >= 8) LoginSucess else LoginError
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextWithIcon(
                                textValue = "1 letra maiúscula",
                                iconName = if (containsUpperCase(password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsUpperCase(password)) LoginSucess else LoginError
                            )
                            TextWithIcon(
                                textValue = "1 letra minúscula",
                                iconName = if (containsLowerCase(password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsLowerCase(password)) LoginSucess else LoginError
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextWithIcon(
                                textValue = "1 número",
                                iconName = if (containsDigit(password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsDigit(password)) LoginSucess else LoginError
                            )
                            TextWithIcon(
                                textValue = "1 caractere especial",
                                iconName = if (containsSpecialCharacter(password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsSpecialCharacter(password)) LoginSucess else LoginError
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                /***** BOTÕES *****/
                ButtonFilled(
                    textButton = "REGISTRAR-SE",
                    colorButton = LoginPri,
                    onClick = onCreateClick
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}