package com.codek.loginapp

import android.app.Application
import com.codek.loginapp.di.appModule
import com.codek.loginapp.di.networkModule
import com.codek.loginapp.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LoginAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@LoginAppApplication)
            modules(
                listOf(
                appModule,
                networkModule,
                repositoryModule
                )
            )
        }
    }
}