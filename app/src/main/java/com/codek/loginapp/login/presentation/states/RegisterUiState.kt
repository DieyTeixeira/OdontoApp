package com.codek.loginapp.login.presentation.states

data class RegisterUiState(
    val nome: String = "",
    val email: String = "",
    val password: String = "",
    val passwordCharError: String? = null,
    val error: String? = null,
    val isShowPassword: Boolean = false,
    val onNameChange: (String) -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onTogglePasswordVisibility: () -> Unit = {},
)