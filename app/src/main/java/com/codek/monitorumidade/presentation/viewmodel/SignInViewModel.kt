package com.codek.monitorumidade.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.monitorumidade.data.repository.LoginRepository
import com.codek.monitorumidade.presentation.states.SignInUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val loginRepository: LoginRepository
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

    fun signIn() {
        val email = _uiState.value.email
        val password = _uiState.value.senha

        viewModelScope.launch {
            // Validações básicas
            if (email.isEmpty()) {
                _uiState.update { it.copy(error = "Por favor, insira seu email.") }
                return@launch
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _uiState.update { it.copy(error = "Formato de email inválido.") }
                return@launch
            }

            if (password.isEmpty()) {
                _uiState.update { it.copy(error = "Por favor, insira sua senha.") }
                return@launch
            }

            try {
                // Chama o repositório para fazer login
                val loginResponse = loginRepository.getLogin(email, password)

                // Se o login for bem-sucedido, atualiza o estado
                _signInIsSucessful.emit(true)
                _uiState.update {
                    it.copy(error = null)
                }

            } catch (e: Exception) {
                // Trate o erro, por exemplo, mostrando uma mensagem de erro
                _uiState.update {
                    it.copy(error = "Falha no login. Verifique suas credenciais.")
                }
            }
        }
    }
}