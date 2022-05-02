package com.example.domain.usecase

import com.example.domain.repository.SensorRepository

class StopMeasureSensorDataUseCase(private val sensorRepository: SensorRepository) {
    operator fun invoke() = sensorRepository.stopRegisterData()
}