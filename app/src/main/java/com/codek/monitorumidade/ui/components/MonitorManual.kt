package com.codek.monitorumidade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MonitorManual() {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = "Ligar/Desligar",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun MonitorManualPreview() {
    MonitorManual()
}