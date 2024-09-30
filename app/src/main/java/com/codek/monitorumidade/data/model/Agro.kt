package com.codek.monitorumidade.data.model

data class Agro(
    val id: Int,
    val usuario: String? = null,
    val umidade: Int,
    val modo: Number,
    val onOff: Number,
    val acionamentos: String? = null
)