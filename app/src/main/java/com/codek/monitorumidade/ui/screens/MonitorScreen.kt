package com.codek.monitorumidade.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Vertices
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.monitorumidade.ui.components.SwitchButton
import com.codek.monitorumidade.ui.viewmodels.MonitorViewModel
import com.codek.monitorumidade.R
import com.codek.monitorumidade.ui.components.MonitorAuto
import com.codek.monitorumidade.ui.components.MonitorManual
import com.codek.monitorumidade.ui.components.MonitorTopBar
import com.codek.monitorumidade.ui.components.MonitorUmidadeDisplay
import com.codek.monitorumidade.ui.theme.VerdeClaro
import com.codek.monitorumidade.ui.theme.VerdeEscuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitorScreen(
    monitorViewModel: MonitorViewModel = viewModel(),
) {
    val switchState by monitorViewModel.switchState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val selectedOption by remember { derivedStateOf { monitorViewModel.selectedOption } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                 expanded = false
            }
    ) {

        MonitorTopBar(
            color = VerdeEscuro
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 8.dp)
                .background(
                    color = VerdeClaro,
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
                        SwitchButton(
                            checkedThumbColor = VerdeEscuro, // Cor do thumb quando ativado
                            uncheckedThumbColor = VerdeEscuro, // Cor do thumb quando desativado
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

        Spacer(modifier = Modifier.height(16.dp))

        MonitorUmidadeDisplay()

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 8.dp, 8.dp, 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (switchState) {
                MonitorAuto()
            } else {
                MonitorManual()
            }
        }
    }
}

@Preview
@Composable
private fun AgrocodekScreenPreview() {
    MonitorScreen(
        monitorViewModel = MonitorViewModel()
    )
}