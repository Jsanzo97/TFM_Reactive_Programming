package com.example.reactiveprogramming.di.sensors

import android.content.Context
import android.hardware.SensorManager
import com.example.reactiveprogramming.ui.sensors.SensorsViewModel
import android.hardware.Sensor as AndroidSensor
import com.example.sensors.Sensor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val BRIGHTNESS_SENSOR = "brightness_sensor"
internal const val ORIENTATION_SENSOR = "orientation_sensor"
internal const val ACCELEROMETER_SENSOR = "accelerometer_sensor"

val sensorModule = module {

    viewModel { SensorsViewModel(
        get(named(BRIGHTNESS_SENSOR)),
        get(named(ORIENTATION_SENSOR)),
        get(named(ACCELEROMETER_SENSOR))) }

    single(named(BRIGHTNESS_SENSOR)) {
        Sensor(
            (androidApplication().applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager),
            AndroidSensor.TYPE_LIGHT
        )
    }

    single(named(ORIENTATION_SENSOR)) {
        Sensor(
            (androidApplication().applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager),
            AndroidSensor.TYPE_ROTATION_VECTOR
        )
    }

    single(named(ACCELEROMETER_SENSOR)) {
        Sensor(
            (androidApplication().applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager),
            AndroidSensor.TYPE_LINEAR_ACCELERATION
        )
    }

}