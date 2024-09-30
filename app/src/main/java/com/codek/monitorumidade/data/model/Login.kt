package com.codek.monitorumidade.data.model

class User(
    val email: String?
)

data class Login(
    val id: Int,
    val email: String,
    val senha: String
)