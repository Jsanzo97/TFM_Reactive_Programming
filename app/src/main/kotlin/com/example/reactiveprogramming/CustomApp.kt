package com.example.reactiveprogramming

import android.app.Application
import com.example.reactiveprogramming.di.data.dataModule
import com.example.reactiveprogramming.di.dataManagement.dataManagementModule
import com.example.reactiveprogramming.di.home.homeModule
import com.example.reactiveprogramming.di.sensors.sensorModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CustomApp)

            modules(
                listOf(
                    dataModule,
                    homeModule,
                    dataManagementModule,
                    sensorModule
                )
            )
        }

        AndroidThreeTen.init(applicationContext)
    }

}