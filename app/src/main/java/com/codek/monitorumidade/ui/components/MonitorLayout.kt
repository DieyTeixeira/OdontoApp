package com.codek.monitorumidade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codek.monitorumidade.ui.controladores.ControladorLuz
import com.codek.monitorumidade.ui.controladores.ControladorTemperatura
import com.codek.monitorumidade.ui.controladores.ControladorUmidade
import com.codek.monitorumidade.ui.states.UiStateIndicator

@Composable
fun MonitorLayout() {

    Column(
        modifier = Modifier
            .size(500.dp)
            .padding(5.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier.weight(1f),
        ) {

            Box(
                modifier = Modifier.weight(3f)
            ) {
                ControladorUmidade()
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 8.dp),
                color = Color.LightGray
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ControladorTemperatura()
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(bottom = 8.dp),
                    color = Color.LightGray
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ControladorLuz()
                }

            }
        }
    }
}

@Preview
@Composable
fun MonitorLayoutPreview() {
    MonitorLayout()
}