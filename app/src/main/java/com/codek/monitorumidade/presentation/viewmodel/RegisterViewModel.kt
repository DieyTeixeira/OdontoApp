package com.codek.monitorumidade.presentation.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.codek.monitorumidade.presentation.states.RegisterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiStateMessage = MutableStateFlow(RegisterUiState())
    val uiStateMessage = _uiStateMessage.asStateFlow()
    private val _signUpIsSucessful = MutableSharedFlow<Boolean>()
    val signUpIsSucessful = _signUpIsSucessful.asSharedFlow()

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
                            password = password,
                            passwordCharError = passwordCharError
                        )
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

    suspend fun signUp() {
        try {
            if (!Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()) {
                _uiState.update {
                    it.copy(
                        error = "Formato de email inválido."
                    )
                }
                return
            }

            if (_uiState.value.password.length < 6) {
                _uiState.update {
                    it.copy(
                        error = "A senha deve ter no mínimo 6 caracteres"
                    )
                }
                return
            }

            _uiState.update {
                it.copy(
                    error = null
                )
            }

            delay(3000)

            _uiState.update {
                it.copy(
                    error = null
                )
            }
        } catch (e: Exception) {
            Log.e("SignUpViewModel", "signUp: ",e )
            _uiState.update {
                it.copy(
                    error = "Erro ao cadastrar usuário"
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
        }
    }

    fun signOut() {

    }

    suspend fun confirmSignUpSuccess() {
        _signUpIsSucessful.emit(false) // Reseta o estado após a confirmação
    }
}