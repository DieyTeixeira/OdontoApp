package com.codek.monitorumidade.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.monitorumidade.data.model.Agro
import com.codek.monitorumidade.data.model.AgroResult
import com.codek.monitorumidade.data.model.Login
import com.codek.monitorumidade.data.model.LoginResponse
import com.codek.monitorumidade.data.repository.AgroRepository
import com.codek.monitorumidade.presentation.states.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppAgroViewModel(
    private val agroRepository: AgroRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    val userId = preferences.getInt("userId", 0)
    val nome = preferences.getString("nome", "") ?: ""

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    var selectedOption by mutableStateOf("Manual")
        private set

    private val _switchState = MutableStateFlow(false)
    val switchState: StateFlow<Boolean> = _switchState

    private val _createName = MutableStateFlow(false)
    val createName: StateFlow<Boolean> = _createName

    private val _agroInfoList = MutableStateFlow<List<AgroResult>>(emptyList())
    val agroInfoList: StateFlow<List<AgroResult>> = _agroInfoList

    private val _humidityValue = MutableStateFlow<Float?>(null)
    val humidityValue: StateFlow<Float?> = _humidityValue

    fun updateHumidity(value: Float) {
        _humidityValue.value = value
    }

    fun setSwitchState(state: Boolean) {
        _switchState.value = state
        updateSelectedOption(if (state) "Automático" else "Manual")
    }

    fun updateSelectedOption(option: String) {
        selectedOption = option
    }

    private val _agroData = MutableStateFlow<Agro?>(null)
    val agroData: StateFlow<Agro?> = _agroData

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadAgroData()
        Log.d("AppAgroViewModel", "init")
        if (nome.isEmpty()) {
            _createName.value = true
        }
    }

    fun loadAgroData() {
        viewModelScope.launch {
            Log.d("AppAgroViewModel", "loadAgroData id: $userId")
            try {
                val result: List<AgroResult> = agroRepository.getAgroById(userId)
                Log.d("AppAgroViewModel", "loadAgroData response: $result")

                val processedList = result.map { agro ->
                    AgroResult(
                        id = agro.id,
                        umidade = agro.umidade,
                        equipamento = agro.equipamento,
                    )
                }
                _agroInfoList.value = processedList
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}