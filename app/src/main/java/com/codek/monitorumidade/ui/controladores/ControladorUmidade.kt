package com.codek.monitorumidade.ui.controladores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codek.monitorumidade.ui.states.UiStateIndicator

@Composable
fun ControladorUmidade() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SpeedTestScreen(
            UiStateIndicator(
                speed = "",
                maxHumidity = "",
                arcValue = 0.43f,
            )
        )
    }
}

@Preview
@Composable
private fun ControladorUmidadePreview() {
    ControladorUmidade()
}