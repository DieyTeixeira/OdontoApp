package com.codek.monitorumidade.presentation.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.codek.monitorumidade.R

// Definição de componente - ** RODAPÉ **
@Composable
fun FooterBar(
//    showDialogVersion: MutableState<Boolean>,
    color: Color
){
    val context = LocalContext.current

    Column {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFFFFFFF))
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://codekst.com.br"))
                    context.startActivity(intent)
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_codek_black),
                    contentDescription = "Logo",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "CODEK",
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Versão 1.0.0",
                    style = TextStyle(
                        color = color,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    ),
//                    modifier = Modifier.clickable {
//                        showDialogVersion.value = true
//                    }
                )
                Spacer(modifier = Modifier.width(25.dp))
            }
        }
    }
}

// Visualização
@Preview
@Composable
fun BaseboardPreview(modifier: Modifier = Modifier) {
    val showDialogVersion = remember { mutableStateOf(false) }
    Box(
        modifier
            .background(color = Color(0xFF000000))
    ) {
        FooterBar(
//            showDialogVersion = showDialogVersion,
            color = Color.LightGray)
    }
}