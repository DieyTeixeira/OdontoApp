package com.codek.monitorumidade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.codek.monitorumidade.data.api.AgroApi
import com.codek.monitorumidade.data.api.ApiCreateAgro
import com.codek.monitorumidade.data.repository.AgroRepositoryImpl
import com.codek.monitorumidade.presentation.ui.screens.AppAgroScreen
import com.codek.monitorumidade.presentation.ui.screens.MonitorAgroScreen2
import com.codek.monitorumidade.presentation.ui.theme.MonitorUmidadeTheme
import com.codek.monitorumidade.presentation.viewmodel.AgroViewModel
import com.codek.monitorumidade.ui.viewmodels.MonitorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonitorUmidadeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppAgroScreen()
                }
            }
        }
    }
}