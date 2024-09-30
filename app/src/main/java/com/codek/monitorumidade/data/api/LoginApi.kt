package com.codek.monitorumidade.data.api

import com.codek.monitorumidade.data.model.Agro
import com.codek.monitorumidade.data.model.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginApi {

    @GET("login")
    suspend fun getLogin(
        @Query("email") email: String,
        @Query("senha") senha: String
    ): Login

    @GET("login/{id}")
    suspend fun getLoginById(@Path("id") id: Int): Login

    @POST("login")
    suspend fun createLogin(@Body login: Login): Login

    @DELETE("login/{id}")
    suspend fun deleteLogin(@Path("id") id: Int): Response<Unit?>


    @PUT("login/{id}")
    suspend fun updateLogin(@Path("id") id: Int, @Body login: Login): Login

}