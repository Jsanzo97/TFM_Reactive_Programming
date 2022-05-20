package com.example.domain.usecase

import com.example.domain.entity.SensorResult
import com.example.domain.repository.SensorRepository

/**
 * Use case that get data from orientation sensor in this moment
 * @param sensorRepository repository to connect with the next layer and be able to perform the operations
 */
class GetOrientationSensorDataUseCase(private val sensorRepository: SensorRepository) {

    /**
     * Executes Use case
     * @return data measured by orientation sensor in this moment
     */
    operator fun invoke(): SensorResult = sensorRepository.getOrientationSensorData()
}