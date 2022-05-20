package com.example.domain.usecase

import com.example.domain.repository.SensorRepository

/**
 * Use case that start the data measure of the sensors
 * @param sensorRepository repository to connect with the next layer and be able to perform the operations
 */
class StartMeasureSensorDataUseCase(private val sensorRepository: SensorRepository) {

    /**
     * Executes Use case
     */
    operator fun invoke() = sensorRepository.startRegisterData()
}