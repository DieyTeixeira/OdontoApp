package com.codek.loginapp.presentation.ui.components

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import com.codek.loginapp.presentation.states.RegisterUiState

@Composable
fun JanelaDialogo(
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