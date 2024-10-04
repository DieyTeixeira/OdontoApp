package com.codek.monitorumidade.presentation.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.monitorumidade.R
import com.codek.monitorumidade.presentation.states.RegisterUiState
import com.codek.monitorumidade.presentation.ui.actions.ButtonClickAction
import com.codek.monitorumidade.presentation.ui.components.FooterBar
import com.codek.monitorumidade.presentation.ui.components.MensagemErro
import com.codek.monitorumidade.presentation.ui.theme.DarkGradient
import com.codek.monitorumidade.presentation.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    color: Color = Color.White,
    uiState: RegisterUiState,
    onCreateClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val buttonClickAction = remember { ButtonClickAction() }
    val focusManager = LocalFocusManager.current
    val password = uiState.password
    val confirmPassword = uiState.confirmPassword
    val showDialog = remember { mutableStateOf(false) }
    val preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    val isPasswordValid = password.length >= 8 &&
            containsUpperCase(password) &&
            containsLowerCase(password) &&
            containsDigit(password) &&
            containsSpecialCharacter(password) &&
            password == confirmPassword

    val isError = uiState.error != null
    val uiStateError = uiState.error ?: ""

//    LaunchedEffect(isError) {
//        if (isError) {
//            vibrateAction(context)
//        }
//    }

    LaunchedEffect(Unit) {
        viewModel.showSaveCredentialsDialog.collect { insertValue ->
            showDialog.value = insertValue
        }
    }

    if (showDialog.value) {
        JanelaDialogo(showDialog, uiState, preferences, onBackClick)
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
            MensagemErro(isError, uiStateError)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            if (buttonClickAction.offClick()) {
                                focusManager.clearFocus()
                                onBackClick()
                            }
                        }
                    )
            )
        }

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
                    "Email",
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
        /***** CAMPO CONFIRMAÇÃO SENHA *****/
        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = uiState.onConfirmPasswordChange,
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
                    text = "Confirmar Senha",
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
            isError = uiState.passwordMismatchError != null
        )
        if (uiState.passwordMismatchError != null) {
            Text(
                text = uiState.passwordMismatchError,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextWithIcon(
                textValue = "Sua senha deve conter no mínimo:"
            )
            Spacer(modifier = Modifier.height(3.dp))
            Row {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    TextWithIcon(
                        textValue = "8 caracteres",
                        iconName = if (password.length >= 8) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                        iconColor = if (password.length >= 8) Color.Green else Color.Red
                    )
                    TextWithIcon(
                        textValue = "1 letra maiúscula",
                        iconName = if (containsUpperCase(password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                        iconColor = if (containsUpperCase(password)) Color.Green else Color.Red
                    )
                    TextWithIcon(
                        textValue = "1 letra minúscula",
                        iconName = if (containsLowerCase(password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                        iconColor = if (containsLowerCase(password)) Color.Green else Color.Red
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    TextWithIcon(
                        textValue = "1 número",
                        iconName = if (containsDigit(password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                        iconColor = if (containsDigit(password)) Color.Green else Color.Red
                    )
                    TextWithIcon(
                        textValue = "1 caractere especial",
                        iconName = if (containsSpecialCharacter(password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                        iconColor = if (containsSpecialCharacter(password)) Color.Green else Color.Red
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        /***** BOTÕES *****/
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(35.dp)
                .background(
                    color = if (isPasswordValid) color else Color.Gray,
                    shape = RoundedCornerShape(100)
                )
                .padding(8.dp)
                .clickable(
                    enabled = isPasswordValid,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        if (buttonClickAction.offClick()) {
                            focusManager.clearFocus()
                            onCreateClick()
                        }
                    }
                )
        ) {
            Text(
                text = "Cadastrar",
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        /***** RODAPÉ *****/
        FooterBar(color = Color.LightGray)

    }
}

@Composable
private fun JanelaDialogo(
    showDialog: MutableState<Boolean>,
    uiState: RegisterUiState,
    preferences: SharedPreferences,
    onBackClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text(text = "USUÁRIO CRIADO COM SUCESSO!") },
        text = {
            Column {
                Text(
                    text = "Deseja salvar o email e a senha para preenchimento automático?"
                )
                Text(
                    text = "Foi enviado um email de verificação para:\n${uiState.email}"
                )
                Text(
                    text = "Para ter acesso a sua conta, é preciso confirmar o email.",
                    fontStyle = FontStyle.Italic,
                    color = Color.Red
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                preferences.edit()
                    .putString("email", uiState.email)
                    .putString("password", uiState.password)
                    .putBoolean("isLoggedIn", false)
                    .apply()
                showDialog.value = false
                onBackClick()
                Log.d("RegisterScreen", "Salvo com sucesso ${uiState.email} - ${uiState.password}")
            }) {
                Text(text = "Sim")
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text(text = "Não")
            }
        }
    )
}

@Composable
private fun TextWithIcon(
    textValue: String,
    iconName: ImageVector? = null,
    iconColor: Color? = null
) {
    Spacer(modifier = Modifier.height(5.dp))
    Row {
        if (iconName != null && iconColor != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = iconName,
                contentDescription = null,
                modifier = Modifier
                    .size(15.dp),
                tint = iconColor
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = textValue,
            style = TextStyle.Default.copy(
                fontSize = 12.sp
            ),
            color = Color.LightGray
        )
    }
}

fun containsUpperCase(password: String): Boolean {
    return password.any { it.isUpperCase() }
}

fun containsLowerCase(password: String): Boolean {
    return password.any { it.isLowerCase() }
}

fun containsDigit(password: String): Boolean {
    return password.any { it.isDigit() }
}

fun containsSpecialCharacter(password: String): Boolean {
    val specialChars = "!@#$%^&*()-_=+{}[]|:;\"'<>,.?/~`"
    return password.any { it in specialChars }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        viewModel = viewModel(),
        color = Color.White,
        uiState = RegisterUiState(),
        onCreateClick = {},
        onBackClick = {}
    )
}