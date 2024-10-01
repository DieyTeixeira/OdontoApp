package com.codek.monitorumidade.data.model

data class Login(
    val id: Int? = null,
    val email: String,
    val senha: String? = null,
    val nome: String? = null,
)