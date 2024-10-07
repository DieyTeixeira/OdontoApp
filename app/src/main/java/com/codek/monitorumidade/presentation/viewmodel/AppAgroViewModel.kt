package com.codek.monitorumidade.ui.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.monitorumidade.data.model.AgroResult
import com.codek.monitorumidade.data.repository.AgroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppAgroViewModel(
    private val agroRepository: AgroRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    val userId = preferences.getInt("userId", 0)
    val nome = preferences.getString("nome", "") ?: ""

    private val _createName = MutableStateFlow(false)
    val createName: StateFlow<Boolean> = _createName

    private val _agroInfoList = MutableStateFlow<List<AgroResult>>(emptyList())
    val agroInfoList: StateFlow<List<AgroResult>> = _agroInfoList

    private val _humidityValue = MutableStateFlow<Float?>(0f)
    val humidityValue: StateFlow<Float?> = _humidityValue

    private val _errorMessage = MutableStateFlow<String?>(null)

    init {
        loadAgroData()
        if (nome.isEmpty()) {
            _createName.value = true
        }
    }

    fun loadAgroData() {
        viewModelScope.launch {
            try {
                val result: List<AgroResult> = agroRepository.getAgroById(userId)

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

    fun updateHumidity(value: Float) {
        _humidityValue.value = value
    }
}