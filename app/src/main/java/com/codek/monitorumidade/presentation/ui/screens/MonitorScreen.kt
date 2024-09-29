package com.codek.monitorumidade.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.data.api.AgroApi
import com.codek.monitorumidade.data.api.ApiCreateAgro
import com.codek.monitorumidade.data.repository.AgroRepositoryImpl
import com.codek.monitorumidade.presentation.ui.components.GeneralDisplayMonitor
import com.codek.monitorumidade.presentation.ui.components.MagneticSlider
import com.codek.monitorumidade.presentation.ui.components.MonitorAuto
import com.codek.monitorumidade.presentation.ui.components.MonitorManual
import com.codek.monitorumidade.presentation.viewmodel.AgroViewModel
import com.codek.monitorumidade.ui.viewmodels.MonitorViewModel

@Composable
fun MonitorScreen(
    modifier: Modifier,
    agroViewModel: AgroViewModel
) {

    val timeInt by agroViewModel.timeInt.collectAsState(initial = 1)
    val timeString by agroViewModel.timeString.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Tempo de consulta:  ",
                        style = TextStyle.Default.copy(
                            fontSize = 14.sp,
                            color = Color.White
                        ),
                        fontWeight = FontWeight.Bold
                    )
                }
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = timeString,
                        style = TextStyle.Default.copy(
                            fontSize = 14.sp,
                            color = Color.White
                        ),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                MagneticSlider(
                    value = timeInt?.toInt() ?: 1,
                    onValueChange = { newValue ->
                        agroViewModel.setTimeRequest(newValue)
                        agroViewModel.updateTimeValue(newValue)
                    },
                    agroViewModel = agroViewModel
                )
            }
        }
        GeneralDisplayMonitor()
    }
}

@Preview
@Composable
private fun MonitorScreenPreview() {
    MonitorScreen(
        modifier = Modifier,
        agroViewModel = AgroViewModel(
            AgroRepositoryImpl(
                ApiCreateAgro.createAgro(
                    AgroApi::class.java
                )
            )
        )
    )
}