package com.codek.monitorumidade.data.repository

import com.codek.monitorumidade.data.api.AgroApi
import com.codek.monitorumidade.data.api.LoginApi
import com.codek.monitorumidade.data.model.Agro
import com.codek.monitorumidade.data.model.Login
import kotlinx.coroutines.delay
import retrofit2.Response

interface LoginRepository {
    suspend fun getLogin(email: String, senha: String): Login
    suspend fun getLoginById(id: Int): Login
    suspend fun createLogin(login: Login): Login
    suspend fun deleteLogin(id: Int)
    suspend fun updateLogin(id: Int, login: Login): Login
}

class LoginRepositoryImpl(private val api: LoginApi) : LoginRepository {

    override suspend fun getLogin(email: String, senha: String): Login {
        return api.getLogin(email, senha)
    }

    override suspend fun getLoginById(id: Int): Login {
        return api.getLoginById(id)
    }

    override suspend fun createLogin(login: Login): Login {
        return api.createLogin(login)
    }

    override suspend fun deleteLogin(id: Int) {
        api.deleteLogin(id)
    }

    override suspend fun updateLogin(id: Int, login: Login): Login {
        return api.updateLogin(id, login)
    }
}