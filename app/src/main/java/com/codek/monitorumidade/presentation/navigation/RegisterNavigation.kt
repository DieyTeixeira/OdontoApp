package com.codek.monitorumidade.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.codek.monitorumidade.presentation.ui.screens.RegisterScreen
import com.codek.monitorumidade.presentation.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val registerRoute = "register"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerScreen(
    onNavigationToSignIn: () -> Unit,
    onPopBackStack: () -> Unit
){
    composable(registerRoute){
        val viewModel = koinViewModel<RegisterViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val signUpIsSucessful by viewModel.signUpIsSucessful.collectAsState(false)

        RegisterScreen(
            uiState = uiState,
            onRegisterClick = {}
        )

        if (signUpIsSucessful) {
            CustomAlertDialog(
                onDismissRequest = {},
                onConfirm = {
                    scope.launch {
                        viewModel.confirmSignUpSuccess()
                        onNavigationToSignIn()
                    }
                }
            )
        }
    }
}

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Cadastro Realizado",
                color = Color.White
            )
        },
        text = {
            Text(
                text = "Seu cadastro foi realizado com sucesso!\n" +
                        "Por favor, faça a verificação do seu e-mail para liberar o acesso.",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("OK")
            }
        },
        containerColor = Color.DarkGray,
    )
}

fun NavHostController.navigateToRegister() {
    navigate(registerRoute)
}