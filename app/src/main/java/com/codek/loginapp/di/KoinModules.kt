package com.codek.loginapp.di

import com.codek.loginapp.data.api.LoginApi
import com.codek.loginapp.data.repository.LoginRepository
import com.codek.loginapp.data.repository.LoginRepositoryImpl
import com.codek.loginapp.presentation.viewmodel.RegisterViewModel
import com.codek.loginapp.presentation.viewmodel.SignInViewModel
import com.codek.loginapp.presentation.viewmodel.SignOutViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
}

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
}

val appModule = module {
    single {
        androidContext().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
    }

    viewModel { SignInViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { SignOutViewModel(get()) }
}