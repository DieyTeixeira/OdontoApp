package com.codek.monitorumidade.data.api

import com.codek.monitorumidade.data.model.Agro
import com.codek.monitorumidade.data.model.AgroResult
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

    @GET("agro/usuario/{id}")
    suspend fun getAgroById(@Path("id") id: Int): List<AgroResult>

    @POST("agro")
    suspend fun createAgro(@Body agro: Agro): Agro

    @DELETE("agro/{id}")
    suspend fun deleteAgro(@Path("id") id: Int): Response<Unit?>

    @PUT("agro/{id}")
    suspend fun updateAgro(@Path("id") id: Int, @Body agro: Agro): Agro

}