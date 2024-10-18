package com.codek.loginapp.data.repository

import com.codek.loginapp.data.api.LoginApi
import com.codek.loginapp.data.model.Login
import com.codek.loginapp.data.model.LoginResponse
import retrofit2.Response

interface LoginRepository {
    suspend fun createLogin(loginRequest: Login): Response<LoginResponse>
    suspend fun updateName(id: Int, loginRequest: Login): LoginResponse
    suspend fun enterLogin(loginRequest: Login): List<Login>
}

class LoginRepositoryImpl(private val api: LoginApi) : LoginRepository {

    override suspend fun createLogin(loginRequest: Login): Response<LoginResponse> {
        return api.createLogin(loginRequest)
    }

    override suspend fun updateName(id: Int, loginRequest: Login): LoginResponse {
        return api.updateName(id, loginRequest)
    }

    override suspend fun enterLogin(loginRequest: Login): List<Login> {
        return api.enterLogin(loginRequest)
    }
}