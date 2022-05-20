package com.example.domain.usecase

import com.example.domain.repository.SensorRepository

/**
 * Use case that reset the data of the sensors
 * @param sensorRepository repository to connect with the next layer and be able to perform the operations
 */
class ResetMeasureSensorDataUseCase(private val sensorRepository: SensorRepository) {

    /**
     * Executes Use case
     */
    operator fun invoke() = sensorRepository.resetDataCount()
}