package com.example.domain.usecase

import com.example.domain.entity.SensorResult
import com.example.domain.repository.SensorRepository

class GetAccelerometerSensorDataUseCase(private val sensorRepository: SensorRepository) {
    operator fun invoke(): SensorResult = sensorRepository.getAccelerometerSensorData()
}