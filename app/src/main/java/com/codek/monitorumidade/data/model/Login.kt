package com.codek.monitorumidade.data.model

data class Login(
    val id: Int? = null,
    val email: String? = null,
    val senha: String? = null,
    val nome: String? = null,
    val erro: String? = null,
    val message: String? = null,
    val validado: Boolean? = null
)

data class LoginResponse(
    val erro: String?,
    val message: String?,
    val validado: Boolean?
)