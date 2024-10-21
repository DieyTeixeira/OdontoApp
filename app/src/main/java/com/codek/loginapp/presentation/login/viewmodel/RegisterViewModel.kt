package com.codek.loginapp.login.presentation.viewmodel

import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.loginapp.data.model.Login
import com.codek.loginapp.data.model.LoginResponse
import com.codek.loginapp.data.repository.LoginRepository
import com.codek.loginapp.login.presentation.states.RegisterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: LoginRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _signUpIsSucessful = MutableSharedFlow<Boolean>()
    val signInIsSucessful = _signUpIsSucessful.asSharedFlow()

    private val _showSaveCredentialsDialog = MutableStateFlow(false)
    val showSaveCredentialsDialog = _showSaveCredentialsDialog.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onNameChange = { nome -> _uiState.update { it.copy(nome = nome) } },
                onEmailChange = { user -> _uiState.update {it.copy(email = user) } },
                onPasswordChange = { password -> _uiState.update { it.copy(password = password) } },
                onTogglePasswordVisibility = {
                    _uiState.update { it.copy(isShowPassword = !_uiState.value.isShowPassword) }
                }
            )
        }
    }

    suspend fun register() {
        val nome = _uiState.value.nome
        val email = _uiState.value.email
        val senha = _uiState.value.password

        if (!validateField(nome.isEmpty(), "Por favor, insira seu nome.")) return
        if (!validateField(email.isEmpty(), "Por favor, insira seu email.")) return
        if (!validateField(!Patterns.EMAIL_ADDRESS.matcher(email).matches(), "Formato de email inválido.")) return

        try {
            val loginRequest = Login(nome = nome, email = email, senha = senha)
            val response = repository.createLogin(loginRequest)

            val message = response.body()?.message

            if (response.isSuccessful && response.code() == 201) {
                _signUpIsSucessful.emit(true)
                _showSaveCredentialsDialog.value = true
            } else {
                _signUpIsSucessful.emit(false)
                message?.let { showError(it) }
            }
        } catch (e: Exception) {
            _signUpIsSucessful.emit(false)
            showError("Erro: ${e.message}")
        }
    }

    private fun showError(message: String) {
        _uiState.update { it.copy(error = message) }
        viewModelScope.launch {
            delay(3000)
            _uiState.update { it.copy(error = null) }
        }
    }

    private fun validateField(condition: Boolean, errorMessage: String): Boolean {
        return if (condition) {
            showError(errorMessage)
            false
        } else {
            true
        }
    }

    fun saveCredentials() {
        preferences.edit()
            .putString("email", _uiState.value.email)
            .putString("password", _uiState.value.password)
            .apply()
    }

    fun clearFields() {
        _uiState.value = uiState.value.copy(
            nome = "",
            email = "",
            password = ""
        )
    }
}