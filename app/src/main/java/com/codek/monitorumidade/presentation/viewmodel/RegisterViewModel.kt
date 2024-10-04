package com.codek.monitorumidade.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.monitorumidade.data.model.Login
import com.codek.monitorumidade.data.model.LoginResponse
import com.codek.monitorumidade.data.repository.LoginRepository
import com.codek.monitorumidade.presentation.states.RegisterUiState
import com.codek.monitorumidade.presentation.states.SignInUiState
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
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    private val _signUpIsSucessful = MutableSharedFlow<Boolean>()
    val signInIsSucessful = _signUpIsSucessful.asSharedFlow()

    private val _showSaveCredentialsDialog = MutableSharedFlow<Boolean>()
    val showSaveCredentialsDialog = _showSaveCredentialsDialog.asSharedFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { user ->
                    _uiState.update {
                        it.copy(email = user)
                    }
                },
                onPasswordChange = { password ->
                    _uiState.update {
                        it.copy(password = password)
                    }
                },
                onConfirmPasswordChange = { confirmPassword ->
                    val passwordMismatchError = if (confirmPassword != _uiState.value.password) {
                        "As senhas não coincidem"
                    } else {
                        null
                    }
                    _uiState.update {
                        it.copy(
                            confirmPassword = confirmPassword,
                            passwordMismatchError = passwordMismatchError
                        )
                    }
                },
                onTogglePasswordVisibility = {
                    _uiState.update {
                        it.copy(isShowPassword = !_uiState.value.isShowPassword)
                    }
                }
            )
        }
    }

    suspend fun register() {
        val email = _uiState.value.email
        val senha = _uiState.value.password

        if (!validateField(email.isEmpty(), "Por favor, insira seu email.")) return
        if (!validateField(!Patterns.EMAIL_ADDRESS.matcher(email).matches(), "Formato de email inválido.")) return
        if (!validateField(!containsSequentialChars(senha), "A senha não pode conter sequências\ncomo '12345' ou 'abcdef'.")) return

        try {
            val loginRequest = Login(email = email, senha = senha)
            val result: LoginResponse = repository.createLogin(loginRequest)

            if (result.message != null) {
                _signUpIsSucessful.emit(true)
                _showSaveCredentialsDialog.emit(true)
            } else {
                showError("Erro ao fazer login")
            }
        } catch (e: Exception) {
            showError(e.message?: "Erro ao fazer login")
        }
    }

    suspend fun insertName(
        nome: String
    ) {
        val id = preferences.getInt("userId", 0)

        try {
            val loginRequest = Login(nome = nome)
            val result: LoginResponse = repository.updateName(id, loginRequest)

            if (result.message == "Usuário alterado com sucesso!") {
                showError(result.message)
                _uiState.update { it.copy(nome = result.message) }
                preferences.edit()
                    .putString("nome", nome)
                    .apply()
            } else {
                showError("Erro ao fazer login")
            }
        } catch (e: Exception) {
            showError(e.message?: "Erro ao fazer login")
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

    fun containsSequentialChars(password: String): Boolean {
        val sequences = listOf(
            "123456789",
            "abcdefghijklmnopqrstuvwxyz"
        )

        val lowerCasePassword = password.lowercase()

        return sequences.any { sequence ->
            if (sequence.length > 2) {
                (0..sequence.length - 1).any { start ->
                    lowerCasePassword.contains(sequence.substring(start))
                }
            } else {
                false
            }
        }
    }

    fun loadSavedCredentials() {
        val savedEmail = preferences.getString("email", null)
        val savedPassword = preferences.getString("password", null)

        if (savedEmail != null) {
            _email.value = savedEmail
        }

        if (savedPassword != null) {
            _password.value = savedPassword
        }
    }
}