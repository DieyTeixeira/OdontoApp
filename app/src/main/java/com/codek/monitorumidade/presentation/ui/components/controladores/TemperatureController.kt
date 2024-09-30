package com.codek.monitorumidade.presentation.ui.components.controladores

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.R

@Composable
fun TemperatureController() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "TEMPERATURA",
            fontSize = 14.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.temperature),
                    contentDescription = "Temperatura",
                    modifier = Modifier.size(45.dp)
                )
            }
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "25°",
                    fontSize = 25.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun TemperatureControllerPreview() {
    TemperatureController()
}