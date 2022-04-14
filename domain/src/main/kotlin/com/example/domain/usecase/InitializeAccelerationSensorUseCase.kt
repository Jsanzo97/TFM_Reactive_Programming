package com.example.domain.usecase

import com.example.domain.repository.SensorRepository

class InitializeAccelerationSensorUseCase(private val sensorRepository: SensorRepository) {
    operator fun invoke() = sensorRepository.initializeAccelerationSensor()
}