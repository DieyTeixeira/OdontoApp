package com.codek.monitorumidade.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.R
import com.codek.monitorumidade.ui.theme.VerdeEscuro

@Composable
fun MonitorTopBar(
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 10.dp, end = 10.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "AGROCODEK",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = "Monitoramento e controle de umidade",
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logocodek_allwhite),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
    }
}