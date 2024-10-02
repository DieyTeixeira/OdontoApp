package com.codek.monitorumidade.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.monitorumidade.data.model.Agro
import com.codek.monitorumidade.data.repository.AgroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppAgroViewModel(
    private val agroRepository: AgroRepository
) : ViewModel() {

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

    private val _agroData = MutableStateFlow<Agro?>(null)
    val agroData: StateFlow<Agro?> = _agroData

    private val _humidityValue = MutableStateFlow<Int?>(null)
    val humidityValue: StateFlow<Int?> = _humidityValue

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        Log.d("AgroViewModel", "Init called, loading data...")
        loadAgroData(1)
    }

    fun loadAgroData(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("AgroViewModel", "Loading data for Agro with id: $id")
                val response = agroRepository.getAgroById(id)
                val responseSizeInBytes = response.toString().toByteArray().size
                Log.d("API Response Size", "Response size: $responseSizeInBytes bytes")
                Log.d("AgroViewModel", "Loaded data: $response")
                _humidityValue.value = response.umidade
                _agroData.value = response
                Log.d("AgroViewModel", "Humidity Value set to: ${_humidityValue.value}")
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("AgroViewModel", "Error loading data: ${e.message}")
            }
        }
    }
}