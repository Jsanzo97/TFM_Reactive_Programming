package com.example.reactiveprogramming.di.sensors

import android.content.Context
import android.hardware.SensorManager
import com.example.domain.usecase.*
import com.example.reactiveprogramming.ui.sensors.SensorsViewModel
import com.example.sensors.Sensor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import android.hardware.Sensor as AndroidSensor

internal const val BRIGHTNESS_SENSOR = "brightness_sensor"
internal const val ORIENTATION_SENSOR = "orientation_sensor"
internal const val ACCELEROMETER_SENSOR = "accelerometer_sensor"

/**
 * Used to create the necessary use cases to create the SensorsViewModel and make it available
 * to be injected into SensorsFragment
 *
 * Also creates one sensor of each type (Brightness, Orientation and Acceleration)
 */
val sensorModule = module {

    viewModel { SensorsViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }

    factory { GetBrightnessSensorFlowUseCase(get()) }
    factory { GetOrientationSensorFlowUseCase(get()) }
    factory { GetAccelerometerSensorFlowUseCase(get()) }
    factory { GetBrightnessSensorDataUseCase(get()) }
    factory { GetOrientationSensorDataUseCase(get()) }
    factory { GetAccelerometerSensorDataUseCase(get()) }
    factory { StartMeasureSensorDataUseCase(get()) }
    factory { StopMeasureSensorDataUseCase(get()) }
    factory { ResetMeasureSensorDataUseCase(get()) }

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