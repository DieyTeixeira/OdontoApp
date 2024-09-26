package com.codek.monitorumidade.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MonitorViewModel : ViewModel() {

    var selectedOption by mutableStateOf("Manual")
        private set

    private val _switchState = MutableStateFlow(false)
    val switchState: StateFlow<Boolean> = _switchState
    fun setSwitchState(state: Boolean) {
        _switchState.value = state
        updateSelectedOption(if (state) "Automático" else "Manual")
    }

    fun updateSelectedOption(option: String) {
        selectedOption = option
    }
}