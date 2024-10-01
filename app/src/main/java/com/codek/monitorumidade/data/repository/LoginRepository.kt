package com.codek.monitorumidade.data.repository

import com.codek.monitorumidade.data.api.LoginApi
import com.codek.monitorumidade.data.model.Login

interface LoginRepository {
    suspend fun enterLogin(loginRequest: Login): List<Login>
}

class LoginRepositoryImpl(private val api: LoginApi) : LoginRepository {
    override suspend fun enterLogin(loginRequest: Login): List<Login> {
        return api.enterLogin(loginRequest)
    }
}