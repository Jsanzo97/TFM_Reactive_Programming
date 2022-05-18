package com.example.reactiveprogramming

import android.app.Application
import com.example.reactiveprogramming.di.collections.collectionsModule
import com.example.reactiveprogramming.di.data.dataModule
import com.example.reactiveprogramming.di.dataManagement.dataManagementModule
import com.example.reactiveprogramming.di.home.homeModule
import com.example.reactiveprogramming.di.sensors.sensorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * This class is used to initiate the application, it will start the koin dependencies manager with all the necessaries modules
 */
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
                    sensorModule,
                    collectionsModule
                )
            )
        }
    }
}