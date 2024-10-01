package com.codek.monitorumidade

import android.app.Application
import com.codek.monitorumidade.di.appModule
import com.codek.monitorumidade.di.networkModule
import com.codek.monitorumidade.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MonitorUmidadeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MonitorUmidadeApplication)
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