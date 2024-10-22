package com.codek.odontoapp.login.data.api

import com.codek.odontoapp.login.data.model.Login
import com.codek.odontoapp.login.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LoginApi {

    @POST("usuario") // Criar usuario
    suspend fun createLogin(@Body loginRequest: Login): Response<LoginResponse>

    @PUT("usuario/{id}") // Atualizar nome
    suspend fun updateName(@Path("id") id: Int, @Body loginRequest: Login): LoginResponse

    @POST("login") // Sign In
    suspend fun enterLogin(@Body loginRequest: Login): List<Login>

}