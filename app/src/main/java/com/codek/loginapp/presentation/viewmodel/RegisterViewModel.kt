package com.codek.loginapp.presentation.viewmodel

import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.loginapp.data.model.Login
import com.codek.loginapp.data.model.LoginResponse
import com.codek.loginapp.data.repository.LoginRepository
import com.codek.loginapp.presentation.states.RegisterUiState
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
                onNameChange = { nome ->
                    _uiState.update {
                        it.copy(nome = nome)
                    }
                },
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
                onTogglePasswordVisibility = {
                    _uiState.update {
                        it.copy(isShowPassword = !_uiState.value.isShowPassword)
                    }
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
//        if (!validateField(!containsSequentialChars(senha), "A senha não pode conter sequências\ncomo '12345' ou 'abcdef'.")) return

        try {
            val loginRequest = Login(nome = nome, email = email, senha = senha)
            val response = repository.createLogin(loginRequest)

            val message = response.body()?.message

            if (response.isSuccessful) {
                when (response.code()) {
                    201 -> {
                        _signUpIsSucessful.emit(true)
                        _showSaveCredentialsDialog.emit(true)
                        Log.d("LoginViewModel", "Sucesso 201: ${response.body()?.message}")
                    }
                    else -> {
                        _signUpIsSucessful.emit(false)
                        message?.let { showError(it) }
                        Log.d("LoginViewModel", "Erro inesperado: ${response.body()?.message}")
                    }
                }
            } else {
                when (response.code()) {
                    400 -> {
                        _signUpIsSucessful.emit(false)
                        message?.let { showError(it) }
                        Log.d("LoginViewModel", "Erro 400: ${response.errorBody()?.string()}")
                    }
                    402 -> {
                        _signUpIsSucessful.emit(false)
                        message?.let { showError(it) }
                        Log.d("LoginViewModel", "Erro 402: ${response.errorBody()?.string()}")
                    }
                    422 -> {
                        _signUpIsSucessful.emit(false)
                        message?.let { showError(it) }
                        Log.d("LoginViewModel", "Erro 422: ${response.errorBody()?.string()}")
                    }
                    else -> {
                        _signUpIsSucessful.emit(false)
                        message?.let { showError(it) }
                        Log.d("LoginViewModel", "Erro inesperado: ${response.errorBody()?.string()}")
                    }
                }
            }
        } catch (e: Exception) {
            _signUpIsSucessful.emit(false)
            showError("Erro: ${e.message}")
            Log.d("LoginViewModel", "Erro desconhecido: ${e.message}")
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

//    fun containsSequentialChars(password: String): Boolean {
//        val sequences = listOf(
//            "123456789",
//            "abcdefghijklmnopqrstuvwxyz"
//        )
//
//        val lowerCasePassword = password.lowercase()
//
//        return sequences.any { sequence ->
//            (0..sequence.length - 3).any { start ->
//                val subSequence = sequence.substring(start, start + 3)
//                lowerCasePassword.windowed(3).any { window ->
//                    window == subSequence
//                }
//            }
//        }
//    }

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

    fun clearFields() {
        _uiState.value = uiState.value.copy(
            nome = "",
            email = "",
            password = ""
        )
    }
}