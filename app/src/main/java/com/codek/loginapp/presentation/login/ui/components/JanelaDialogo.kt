package com.codek.loginapp.presentation.login.ui.components

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
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.ui.window.Dialog
import com.codek.loginapp.R
import com.codek.loginapp.login.presentation.states.RegisterUiState
import com.codek.loginapp.login.presentation.ui.theme.LoginError
import com.codek.loginapp.login.presentation.ui.theme.LoginPri
import com.codek.loginapp.login.presentation.ui.theme.LoginSucess
import com.codek.loginapp.login.presentation.viewmodel.RegisterViewModel

@Composable
fun JanelaDialogo(
    email: String,
    onOkClick: () -> Unit,
    onDismissRequest: () -> Unit
) {

    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = email,
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
                ) {
                    Icon(
                        Icons.Outlined.CrisisAlert,
                        contentDescription = "check",
                        tint = LoginError,
                        modifier = Modifier
                            .size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "É necessário fazer a confirmação de email para ter acesso a sua conta.",
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(100)
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onOkClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = "check",
                        tint = Color.Black,
//                            modifier = Modifier
//                                .size(45.dp)
                    )
//                        TextButton(
//                            onClick = { onOkClick() }
//                        ) {
//                            Text(text = "OK", color = Color.Black)
//                        }
                }
            }
        }
    }
}

@Preview
@Composable
private fun JanelaDialogoPreview() {
    JanelaDialogo(
        email = "william.henry@example-store.com",
        onOkClick = {},
        onDismissRequest = {}
    )
}