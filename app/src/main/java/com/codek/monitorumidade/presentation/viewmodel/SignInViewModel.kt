package com.codek.monitorumidade.presentation.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.codek.monitorumidade.data.model.Login
import com.codek.monitorumidade.data.repository.LoginRepository
import com.codek.monitorumidade.presentation.states.SignInUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()
    private val _signInIsSucessful = MutableSharedFlow<Boolean>()
    val signInIsSucessful = _signInIsSucessful.asSharedFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { user ->
                    _uiState.update {
                        it.copy(email = user)
                    }
                },
                onPasswordChange = { password ->
                    val filteredPassword = password.filter { it.isLetterOrDigit() }
                    val passwordCharError = if (password != filteredPassword) {
                        "A senha não deve conter caracteres especiais\nApenas letras (A-Z) e números (0-9)"
                    } else {
                        null
                    }
                    _uiState.update {
                        it.copy(
                            senha = password,
                            passwordCharError = passwordCharError
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

    suspend fun signIn() {
        Log.d("LoginViewModel", "signIn: Entrou aqui")
        val email = _uiState.value.email
        val senha = _uiState.value.senha

        if (email.isEmpty()) {
            _uiState.update {
                it.copy(error = "Por favor, insira seu email.")
            }
            Log.d("LoginViewModel", "signIn: ${_uiState.value.error}")
            delay(3000)
            _uiState.update {
                it.copy(error = null)
            }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update {
                it.copy(error = "Formato de email inválido.")
            }
            Log.d("LoginViewModel", "signIn: ${_uiState.value.error}")
            delay(3000)
            _uiState.update {
                it.copy(error = null)
            }
            return
        }

        if (senha.isEmpty()) {
            _uiState.update {
                it.copy(error = "Por favor, insira sua senha.")
            }
            Log.d("LoginViewModel", "signIn: ${_uiState.value.error}")
            delay(3000)
            _uiState.update {
                it.copy(error = null)
            }
            return
        }

        try {
            val loginRequest = Login(email = email, senha = senha)
            val result: List<Login> = repository.enterLogin(loginRequest)

            if (result.isNotEmpty() && result[0].id != null) {
                _signInIsSucessful.emit(true)
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
}