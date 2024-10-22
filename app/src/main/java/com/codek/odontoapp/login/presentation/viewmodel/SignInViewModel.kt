package com.codek.odontoapp.login.presentation.viewmodel

import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.odontoapp.login.data.model.Login
import com.codek.odontoapp.login.data.repository.LoginRepository
import com.codek.odontoapp.login.presentation.states.SignInUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: LoginRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    private val _signInIsSucessful = MutableSharedFlow<Boolean>()
    val signInIsSucessful = _signInIsSucessful.asSharedFlow()

    private val _resultId = MutableStateFlow<Int?>(null)
    val resultId = _resultId.asStateFlow()

    private val _useCredentials = MutableSharedFlow<Boolean>()
    val useCredentials = _useCredentials.asSharedFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { user -> _uiState.update { it.copy(email = user) } },
                onPasswordChange = { password -> _uiState.update { it.copy(password = password) } },
                onTogglePasswordVisibility = {
                    _uiState.update { it.copy(isShowPassword = !_uiState.value.isShowPassword) }
                }
            )
        }

        viewModelScope.launch {
            _useCredentials.collect { useCredentials ->
                val email = preferences.getString("email", "") ?: ""
                val password = preferences.getString("password", "") ?: ""

                _uiState.update { currentState ->
                    currentState.copy(
                        email = if (useCredentials) email else uiState.value.email,
                        password = if (useCredentials) password else uiState.value.password
                    )
                }
            }
        }
    }

    suspend fun signIn() {
        val email = _uiState.value.email
        val senha = _uiState.value.password

        if (!validateField(email.isEmpty(), "Por favor, insira seu email.")) return
        if (!validateField(!Patterns.EMAIL_ADDRESS.matcher(email).matches(), "Formato de email inválido.")) return
        if (!validateField(senha.isEmpty(), "Por favor, insira sua senha.")) return

        try {
            val loginRequest = Login(email = email, senha = senha)
            val result: List<Login> = repository.enterLogin(loginRequest)

            if (result[0].id != null && result[0].validado == true) {
                result[0].id?.let {
                    preferences.edit()
                        .putString("email", email)
                        .putString("password", senha)
                        .putInt("userId", it)
                        .putString("nome", result[0].nome)
                        .putBoolean("isLoggedIn", true)
                        .apply()
                }
                _resultId.emit(result[0].id)
                _signInIsSucessful.emit(true)
            } else if (result[0].validado == false) {
                showError("Usuário não validado. Acesse seu e-mail!")
            } else {
                showError("Erro ao fazer login")
            }
        } catch (e: Exception) {
            showError("Usuário ou senha incorretos")
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

    fun clearFields() {
        _uiState.update {
            it.copy(
                email = "",
                password = ""
            )
        }
    }
}