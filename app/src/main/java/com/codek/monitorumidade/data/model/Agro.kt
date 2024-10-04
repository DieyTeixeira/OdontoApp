package com.codek.monitorumidade.data.model

data class Agro(
    val id: Int,
    val usuario: String? = null,
    val umidade: Int,
    val modo: Number,
    val onOff: Number,
    val acionamentos: String? = null
)

data class AgroResult(
    val id: Int? = null,
    val equipamento: String? = null,
    val usuario_id: Int? = null,
    val umidade: Int? = null,
    val modo: Number? = null,
    val onOff: Number? = null,
    val nome: String? = null,
)