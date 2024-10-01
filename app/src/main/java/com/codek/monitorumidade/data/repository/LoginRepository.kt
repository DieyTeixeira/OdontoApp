package com.codek.monitorumidade.data.repository

import com.codek.monitorumidade.data.api.LoginApi
import com.codek.monitorumidade.data.model.Login

interface LoginRepository {
    suspend fun createLogin(loginRequest: Login): List<Login>
    suspend fun enterLogin(loginRequest: Login): List<Login>
}

class LoginRepositoryImpl(private val api: LoginApi) : LoginRepository {

    override suspend fun createLogin(loginRequest: Login): List<Login> {
        return api.createLogin(loginRequest)
    }

    override suspend fun enterLogin(loginRequest: Login): List<Login> {
        return api.enterLogin(loginRequest)
    }
}