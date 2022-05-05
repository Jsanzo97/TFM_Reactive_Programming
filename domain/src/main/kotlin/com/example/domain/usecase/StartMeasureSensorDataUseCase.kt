package com.example.domain.usecase

import com.example.domain.repository.SensorRepository

class StartMeasureSensorDataUseCase(private val sensorRepository: SensorRepository) {
    operator fun invoke() = sensorRepository.startRegisterData()
}