package com.example.domain.usecase

import com.example.domain.repository.SensorRepository

class ResetMeasureSensorDataUseCase(private val sensorRepository: SensorRepository) {
    operator fun invoke() = sensorRepository.resetDataCount()
}