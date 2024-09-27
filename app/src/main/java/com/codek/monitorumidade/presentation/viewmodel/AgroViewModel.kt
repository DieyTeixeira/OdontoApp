package com.codek.monitorumidade.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.monitorumidade.data.model.Agro
import com.codek.monitorumidade.data.repository.AgroRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.random.Random

class AgroViewModel(
    private val agroRepository: AgroRepository
) : ViewModel() {

    private val _agroData = MutableStateFlow<Agro?>(null)
    val agroData: StateFlow<Agro?> = _agroData

    private val _humidityValue = MutableStateFlow<Int?>(null)
    val humidityValue: StateFlow<Int?> = _humidityValue

    private val _timeInt = MutableStateFlow<Int?>(null)
    val timeInt: StateFlow<Int?> = _timeInt

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

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

    fun setErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    fun setIsLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setIsRefreshing(refreshing: Boolean) {
        _isRefreshing.value = refreshing
    }

    fun updateTimeValue(newValue: Int) {
        viewModelScope.launch {
            _timeInt.emit(newValue)
        }
    }

    private val _timeData = MutableStateFlow<Long>(30000L) // Valor padrão
    val timeData: StateFlow<Long> = _timeData

    private val _timeString = MutableStateFlow<String>("30 segundos") // Valor padrão
    val timeString: StateFlow<String> = _timeString

    fun setTimeRequest(time: Int?) {
        _timeInt.value = time
        updateTimeData()
        updateTimeString()
    }

    fun updateTimeData() {
        _timeData.value = when (_timeInt.value) {
            1 -> 30000L   // 30 segundos
            2 -> 60000L   // 1 minuto
            3 -> 300000L  // 5 minutos
            4 -> 900000L  // 15 minutos
            5 -> 1800000L // 30 minutos
            else -> 30000L // Valor padrão
        }
    }

    fun updateTimeString() {
        _timeString.value = when (_timeInt.value) {
            1 -> "30 segundos"
            2 -> "1 minuto"
            3 -> "5 minutos"
            4 -> "15 minutos"
            5 -> "30 minutos"
            else -> "30 segundos"
        }
    }
}