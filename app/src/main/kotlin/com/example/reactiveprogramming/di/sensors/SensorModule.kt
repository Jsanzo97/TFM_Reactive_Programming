package com.example.reactiveprogramming.di.sensors

import com.example.domain.usecase.*
import com.example.reactiveprogramming.ui.sensors.SensorsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sensorModule = module {

    viewModel { SensorsViewModel(get(), get(), get(), get(), get(), get()) }

    factory { GetBrightnessSensorFlowUseCase(get()) }
    factory { GetOrientationSensorFlowUseCase(get()) }
    factory { GetAccelerometerSensorFlowUseCase(get()) }
    factory { GetBrightnessSensorDataUseCase(get()) }
    factory { GetOrientationSensorDataUseCase(get()) }
    factory { GetAccelerometerSensorDataUseCase(get()) }

}