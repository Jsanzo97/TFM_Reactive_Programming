package com.example.domain.usecase

import com.example.domain.repository.SensorRepository

class InitializeBrightnessSensorUseCase(private val sensorRepository: SensorRepository) {
    operator fun invoke() = sensorRepository.initializeBrightnessSensor()
}