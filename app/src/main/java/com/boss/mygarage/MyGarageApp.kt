package com.boss.mygarage

import android.app.Application
import com.boss.mygarage.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyGarageApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyGarageApp)
            // Add modules from di package
            modules(listOf(dataModule, repositoryModule, domainModule, viewModelModule))
        }
    }
}