package com.codek.monitorumidade.data.repository

import com.codek.monitorumidade.data.api.LoginApi
import com.codek.monitorumidade.data.model.Login
import com.codek.monitorumidade.data.model.LoginResponse

interface LoginRepository {
    suspend fun createLogin(loginRequest: Login): LoginResponse
    suspend fun updateName(id: Int, loginRequest: Login): LoginResponse
    suspend fun enterLogin(loginRequest: Login): List<Login>
}

class LoginRepositoryImpl(private val api: LoginApi) : LoginRepository {

    override suspend fun createLogin(loginRequest: Login): LoginResponse {
        return api.createLogin(loginRequest)
    }

    override suspend fun updateName(id: Int, loginRequest: Login): LoginResponse {
        return api.updateName(id, loginRequest)
    }

    override suspend fun enterLogin(loginRequest: Login): List<Login> {
        return api.enterLogin(loginRequest)
    }
}