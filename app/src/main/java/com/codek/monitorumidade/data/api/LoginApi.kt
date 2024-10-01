package com.codek.monitorumidade.data.api

import com.codek.monitorumidade.data.model.Login
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("usuario") // Criar usuario
    suspend fun createLogin(@Body loginRequest: Login): List<Login>

    @POST("login") // Sign In
    suspend fun enterLogin(@Body loginRequest: Login): List<Login>

}