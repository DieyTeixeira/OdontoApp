package com.codek.monitorumidade.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.monitorumidade.data.model.Agro
import com.codek.monitorumidade.data.repository.AgroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppAgroViewModel(
    private val agroRepository: AgroRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    val userId = preferences.getInt("userId", 0)

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
        loadAgroData()
    }

    fun loadAgroData() {
        viewModelScope.launch {
            try {
                Log.d("AppAgroViewModel", "Loading Agro Data for ID: $userId")
                val response = agroRepository.getAgroById(userId)
                _humidityValue.value = response.umidade
                Log.d("AppAgroViewModel", "Loaded Agro Data: $response")
                _agroData.value = response
                Log.d("AppAgroViewModel", "Loaded Agro Data: ${_agroData.value}")
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}