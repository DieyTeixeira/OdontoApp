package com.codek.monitorumidade.di

import com.codek.monitorumidade.data.api.AgroApi
import com.codek.monitorumidade.data.api.LoginApi
import com.codek.monitorumidade.data.repository.AgroRepository
import com.codek.monitorumidade.data.repository.AgroRepositoryImpl
import com.codek.monitorumidade.data.repository.LoginRepository
import com.codek.monitorumidade.data.repository.LoginRepositoryImpl
import com.codek.monitorumidade.presentation.viewmodel.SignInViewModel
import com.codek.monitorumidade.ui.viewmodels.MonitorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://codekst.com.br/"

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<LoginApi> {
        get<Retrofit>().create(LoginApi::class.java)
    }

    single<AgroApi> {
        get<Retrofit>().create(AgroApi::class.java)
    }
}

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<AgroRepository> { AgroRepositoryImpl(get()) }
}

val appModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { MonitorViewModel(get()) }
}