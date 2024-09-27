package com.codek.monitorumidade.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.monitorumidade.presentation.ui.theme.Green500
import com.codek.monitorumidade.presentation.ui.theme.Green400
import com.codek.monitorumidade.presentation.viewmodel.AgroViewModel
import kotlin.math.roundToInt

@Composable
fun MagneticSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    agroViewModel: AgroViewModel
) {
    val sliderValue = value.toFloat()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = sliderValue,
            onValueChange = { newValue ->
                val roundedValue = newValue.roundToInt().coerceIn(1, 5)
                onValueChange(roundedValue)
                agroViewModel.setTimeRequest(roundedValue)
                agroViewModel.updateTimeValue(roundedValue)
            },
            valueRange = 1f..5f,
            steps = 3,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Green500, // Cor do thumb
                activeTrackColor = Green400, // Cor da parte ativa do track
                inactiveTrackColor = Color.LightGray // Cor da parte inativa do track
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MagneticSliderPreview() {
    MagneticSlider(
        value = 1,
        onValueChange = {},
        agroViewModel = viewModel()
    )
}