package com.example.intership.app

import android.app.Application
import com.example.intership.di.adapterModule
import com.example.intership.di.repositoryModule
import com.example.intership.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IntershipApp :Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(applicationContext)
            modules(
                viewModelModule,
                adapterModule,
                repositoryModule

            )
        }
    }
}