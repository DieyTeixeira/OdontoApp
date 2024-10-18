package com.codek.loginapp.presentation.ui.components

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.CrisisAlert
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.loginapp.R
import com.codek.loginapp.presentation.states.RegisterUiState
import com.codek.loginapp.presentation.ui.theme.LoginError
import com.codek.loginapp.presentation.ui.theme.LoginPri
import com.codek.loginapp.presentation.ui.theme.LoginSucess
import com.codek.loginapp.presentation.viewmodel.RegisterViewModel

@Composable
fun JanelaDialogo(
    onSimClick: () -> Unit,
    onNaoClick: () -> Unit
) {
    val uiState = RegisterUiState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000)) // Fundo semitransparente para simular um diálogo
            .clickable(
                onClick = { /* Evita fechar o dialogo ao clicar fora */ },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(color = Color.Transparent)
                .padding(25.dp)
        ) {
            // Título
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = LoginSucess,
                        shape = RoundedCornerShape(100)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = "check",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "USUÁRIO CRIADO COM SUCESSO!",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Texto informativo
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Foi enviado um email de verificação para:",
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = uiState.email,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                        Icons.Outlined.CrisisAlert,
                        contentDescription = "check",
                        tint = LoginError,
                        modifier = Modifier
                            .size(35.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Para ter acesso a sua conta, é preciso confirmar o email.",
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Pergunta ao usuário
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Text(
                    text = "Deseja salvar o email e a senha para preenchimento automático na tela de login?",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Botões de confirmação e negação
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .height(35.dp)
                            .width(80.dp)
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(100)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { onNaoClick() }
                        ) {
                            Text(text = "Não", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .height(35.dp)
                            .width(80.dp)
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(100)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { onSimClick() }
                        ) {
                            Text(text = "Sim", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun JanelaDialogoPreview() {
    JanelaDialogo(
        onSimClick = {},
        onNaoClick = {}
    )
}

@Composable
fun CredentialsDialogUse(
    onSimClick: () -> Unit,
    onNaoClick: () -> Unit
){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000)) // Fundo semitransparente para simular um diálogo
            .clickable(
                onClick = { /* Evita fechar o dialogo ao clicar fora */ },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(color = Color.Transparent)
                .padding(25.dp)
        ) {
            // Pergunta ao usuário
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Outlined.CrisisAlert,
                        contentDescription = "check",
                        tint = LoginPri,
                        modifier = Modifier
                            .size(35.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text (
                        text = "Usar credenciais salvas?",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Deseja utilizar o e-mail e senha salvos?",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Botões de confirmação e negação
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .height(35.dp)
                            .width(80.dp)
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(100)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { onNaoClick() }
                        ) {
                            Text(text = "Não", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .height(35.dp)
                            .width(80.dp)
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(100)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { onSimClick() }
                        ) {
                            Text(text = "Sim", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CredentialsDialogPreview() {
    CredentialsDialogUse(
        onSimClick = {},
        onNaoClick = {}
    )
}