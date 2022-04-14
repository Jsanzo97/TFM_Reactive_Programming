package com.example.domain.usecase

import com.example.domain.entity.SensorResult
import com.example.domain.repository.SensorRepository
import kotlinx.coroutines.flow.Flow

class GetOrientationSensorFlowUseCase(private val sensorRepository: SensorRepository) {
    operator fun invoke(): Flow<SensorResult> = sensorRepository.getOrientationSensorFlow()
}