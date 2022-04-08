package com.example.reactiveprogramming

import android.app.Application
import com.example.common.logger.Logger
import com.example.common.logger.TimberLogger
import com.example.reactiveprogramming.di.KoinLogger
import com.example.reactiveprogramming.di.data.dataModule
import com.example.reactiveprogramming.di.dataManagement.dataManagementModule
import com.example.reactiveprogramming.di.home.homeModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CustomApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            val logger : Logger = TimberLogger()

            this.logger(KoinLogger(logger, Level.DEBUG))

            androidContext(this@CustomApp)

            koin.declare(logger)

            modules(
                listOf(
                    dataModule,
                    homeModule,
                    dataManagementModule
                )
            )
        }

        AndroidThreeTen.init(applicationContext)
    }

}