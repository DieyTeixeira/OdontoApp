package com.codek.monitorumidade.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.presentation.ui.components.GeneralDisplayMonitor
import com.codek.monitorumidade.presentation.ui.components.MagneticSlider
import com.codek.monitorumidade.presentation.ui.components.MonitorAuto
import com.codek.monitorumidade.presentation.ui.components.MonitorManual
import com.codek.monitorumidade.presentation.ui.components.SwitchMode
import com.codek.monitorumidade.presentation.ui.components.TopBarMonitor
import com.codek.monitorumidade.presentation.ui.theme.DarkGradient
import com.codek.monitorumidade.presentation.ui.theme.Green500
import com.codek.monitorumidade.presentation.ui.theme.Green700
import com.codek.monitorumidade.presentation.viewmodel.AgroViewModel
import com.codek.monitorumidade.ui.viewmodels.MonitorViewModel

@Composable
fun MonitorAgroScreen(
    monitorViewModel: MonitorViewModel,
    agroViewModel: AgroViewModel
) {
    val switchState by monitorViewModel.switchState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val selectedOption by remember { derivedStateOf { monitorViewModel.selectedOption } }

    val timeInt by agroViewModel.timeInt.collectAsState(initial = 1)
    val timeString by agroViewModel.timeString.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient)
            .padding(bottom = 8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                 expanded = false
            }
    ) {

        TopBarMonitor(
            color = Green700
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 10.dp)
                .background(
                    color = Green500,
                    shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Método: ",
                        style = TextStyle.Default.copy(
                            fontSize = 16.sp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .clickable(
                                onClick = { expanded = !expanded }
                            ),
                        color = Color.White
                    )
                    Text(
                        text = selectedOption,
                        style = TextStyle.Default.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .clickable(
                                onClick = { expanded = !expanded }
                            ),
                        color = Color.White
                    )
                }
                if (expanded) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .align(Alignment.CenterHorizontally)
                            .height(40.dp)
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(
                                    topStart = 10.dp,
                                    bottomStart = 10.dp,
                                    topEnd = 10.dp,
                                    bottomEnd = 10.dp
                                )
                            )
                            .padding(start = 15.dp, end = 15.dp)
                    ) {
                        SwitchMode(
                            checkedThumbColor = Green700, // Cor do thumb quando ativado
                            uncheckedThumbColor = Green700, // Cor do thumb quando desativado
                            checkedTrackColor = Color.Transparent, // Cor do fundo quando ativado
                            uncheckedTrackColor = Color.Transparent, // Cor do fundo quando desativado
                            checkedBorderColor = Color.Transparent, // Remover contorno quando ativado
                            uncheckedBorderColor = Color.Transparent, // Remover contorno quando desativado
                            switchState = switchState,
                            onSwitchChange = { state ->
                                monitorViewModel.setSwitchState(state)
                            }
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
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
                        .weight(1f)
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 8.dp, 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                key(switchState) {
                    if (switchState) {
                        MonitorAuto()
                    } else {
                        MonitorManual()
                    }
                }
            }
        }

    }
}

//@Preview
//@Composable
//private fun AgrocodekScreenPreview() {
//    MonitorAgroScreen(
//        monitorViewModel = MonitorViewModel()
//    )
//}