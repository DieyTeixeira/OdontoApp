package com.codek.monitorumidade.di

import com.codek.monitorumidade.data.repository.LoginRepositoryImpl
import com.codek.monitorumidade.presentation.viewmodel.RegisterViewModel
import com.codek.monitorumidade.presentation.viewmodel.SignInViewModel
import com.codek.monitorumidade.ui.viewmodels.MonitorViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { LoginRepositoryImpl(get()) }
    viewModelOf(::MonitorViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::SignInViewModel)
}