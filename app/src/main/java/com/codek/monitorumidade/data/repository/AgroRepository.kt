package com.codek.monitorumidade.data.repository

import com.codek.monitorumidade.data.api.AgroApi
import com.codek.monitorumidade.data.model.Agro
import com.codek.monitorumidade.data.model.AgroResult
import kotlinx.coroutines.delay
import retrofit2.Response

interface AgroRepository {
    suspend fun getAgro(): List<Agro>
    suspend fun getAgroById(id: Int): List<AgroResult>
    suspend fun createAgro(agro: Agro): Agro
    suspend fun deleteAgro(id: Int)
    suspend fun updateAgro(id: Int, agro: Agro): Agro
}

class AgroRepositoryImpl(private val api: AgroApi) : AgroRepository {
    override suspend fun getAgro(): List<Agro> {
        return api.getAgro()
    }

    override suspend fun getAgroById(id: Int): List<AgroResult> {
        return api.getAgroById(id)
    }

    override suspend fun createAgro(agro: Agro): Agro {
        return api.createAgro(agro)
    }

    override suspend fun deleteAgro(id: Int) {
        api.deleteAgro(id)
    }

    override suspend fun updateAgro(id: Int, agro: Agro): Agro {
        return api.updateAgro(id, agro)
    }
}