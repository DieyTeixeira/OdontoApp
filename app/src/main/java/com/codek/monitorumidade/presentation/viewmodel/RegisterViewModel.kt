package com.codek.monitorumidade.presentation.viewmodel

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
    private val repository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()
    private val _signUpIsSucessful = MutableSharedFlow<Boolean>()
    val signInIsSucessful = _signUpIsSucessful.asSharedFlow()

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
                _uiState.update {
                    it.copy(error = result.message)
                }
                delay(3000)
                _uiState.update {
                    it.copy(error = null)
                }
                _signUpIsSucessful.emit(true)
            } else {
                _uiState.update {
                    it.copy(error = "Erro ao fazer login")
                }
                delay(3000)
                _uiState.update {
                    it.copy(error = null)
                }
            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Error during login: ${e.message}", e)
            _uiState.update {
                it.copy(error = e.message)
            }
            delay(3000)
            _uiState.update {
                it.copy(error = null)
            }
        }
    }

    suspend fun sendPasswordResetEmail() {
    }

    private fun showError(message: String) {
        _uiState.update { it.copy(error = message) }
        Log.d("LoginViewModel", "signIn: $message")
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
}