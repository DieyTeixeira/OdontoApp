package com.codek.monitorumidade.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codek.monitorumidade.presentation.ui.components.GeneralDisplayMonitor

@Composable
fun MonitorScreen(
    modifier: Modifier,
) {

    Column(
        modifier
            .fillMaxWidth()
    ) {
        GeneralDisplayMonitor()
    }
}

@Preview
@Composable
private fun MonitorScreenPreview() {
    MonitorScreen(
        modifier = Modifier
    )
}