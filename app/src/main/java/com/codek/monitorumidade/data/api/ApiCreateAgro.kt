package com.codek.monitorumidade.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCreateAgro {
    private const val BASE_URL = "https://codekst.com.br/"

    fun createAgro(apiClass: Class<AgroApi>): AgroApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(apiClass)
    }
}