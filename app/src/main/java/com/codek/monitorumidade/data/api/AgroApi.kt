package com.codek.monitorumidade.data.api

import com.codek.monitorumidade.data.model.Agro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AgroApi {

    @GET("agro")
    suspend fun getAgro(): List<Agro>

    @GET("agro/longpolling")
    suspend fun getAgroLongPolling(): Response<List<Agro>>

    @GET("agro/{id}")
    suspend fun getAgroById(@Path("id") id: Int): Agro

    @POST("agro")
    suspend fun createAgro(@Body agro: Agro): Agro

    @DELETE("agro/{id}")
    suspend fun deleteAgro(@Path("id") id: Int): Response<Unit?>


    @PUT("agro/{id}")
    suspend fun updateAgro(@Path("id") id: Int, @Body agro: Agro): Agro

}