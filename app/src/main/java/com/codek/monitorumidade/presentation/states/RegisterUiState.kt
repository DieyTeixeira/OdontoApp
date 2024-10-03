package com.codek.monitorumidade.presentation.states

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordCharError: String? = null,
    val confirmPasswordCharError: String? = null,
    val passwordMismatchError: String? = null,
    val error: String? = null,
    val isShowPassword: Boolean = false,
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onConfirmPasswordChange: (String) -> Unit = {},
    val onTogglePasswordVisibility: () -> Unit = {},
)