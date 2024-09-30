package com.codek.monitorumidade.presentation.ui.components.controladores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codek.monitorumidade.data.api.AgroApi
import com.codek.monitorumidade.data.api.ApiCreateAgro
import com.codek.monitorumidade.data.repository.AgroRepositoryImpl
import com.codek.monitorumidade.ui.viewmodels.MonitorViewModel

@Composable
fun HumidityController() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val apiCreateAgro = ApiCreateAgro.createAgro(AgroApi::class.java)
        val repositoryAgro = AgroRepositoryImpl(apiCreateAgro)
        val monitorViewModel = MonitorViewModel(repositoryAgro)

        HumidityIndicator(
            monitorViewModel = monitorViewModel
        )
    }
}

@Preview
@Composable
private fun HumidityControllerPreview() {
    HumidityController()
}