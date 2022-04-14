package com.example.domain.usecase

import com.example.domain.repository.SensorRepository

class InitializeOrientationSensorUseCase(private val sensorRepository: SensorRepository) {
    operator fun invoke() = sensorRepository.initializeOrientationSensor()
}