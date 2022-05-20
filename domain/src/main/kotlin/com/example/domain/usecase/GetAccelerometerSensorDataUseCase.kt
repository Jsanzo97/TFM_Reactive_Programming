package com.example.domain.usecase

import com.example.domain.entity.SensorResult
import com.example.domain.repository.SensorRepository

/**
 * Use case that get data from acceleration sensor in this moment
 * @param sensorRepository repository to connect with the next layer and be able to perform the operations
 */
class GetAccelerometerSensorDataUseCase(private val sensorRepository: SensorRepository) {

    /**
     * Executes Use case
     * @return data measured by acceleration sensor in this moment
     */
    operator fun invoke(): SensorResult = sensorRepository.getAccelerometerSensorData()
}