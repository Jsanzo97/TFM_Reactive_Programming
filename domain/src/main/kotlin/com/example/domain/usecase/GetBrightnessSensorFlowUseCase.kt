package com.example.domain.usecase

import com.example.domain.entity.SensorResult
import com.example.domain.repository.SensorRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case that get data flow from brightness sensor
 * @param sensorRepository repository to connect with the next layer and be able to perform the operations
 */
class GetBrightnessSensorFlowUseCase(private val sensorRepository: SensorRepository) {

    /**
     * Executes Use case
     * @return data flow with the data of the brihgtness sensor
     */
    operator fun invoke(): Flow<SensorResult> = sensorRepository.getBrightnessSensorFlow()
}