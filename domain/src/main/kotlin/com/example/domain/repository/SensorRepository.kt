package com.example.domain.repository

import com.example.domain.entity.SensorResult
import kotlinx.coroutines.flow.Flow

/**
 * Definition of the functions in the Domain layer that we need to implement in the next layer
 */
interface SensorRepository {

    /**
     * Returns the brightness sensor data flow
     * @return flow with brightness sensor data
     */
    fun getBrightnessSensorFlow(): Flow<SensorResult>

    /**
     * Returns the orientation sensor data flow
     * @return flow with orientation sensor data
     */
    fun getOrientationSensorFlow(): Flow<SensorResult>

    /**
     * Returns the acceleration sensor data flow
     * @return flow with acceleration sensor data
     */
    fun getAccelerometerSensorFlow(): Flow<SensorResult>

    /**
     * Returns the brightness sensor data in this moment
     * @return brightness sensor data in this moment
     */
    fun getBrightnessSensorData(): SensorResult

    /**
     * Returns the orientation sensor data in this moment
     * @return brightness orientation data in this moment
     */
    fun getOrientationSensorData(): SensorResult

    /**
     * Returns the acceleration sensor data in this moment
     * @return acceleration sensor data in this moment
     */
    fun getAccelerometerSensorData(): SensorResult

    /**
     * Stop the sensors data measure
     */
    fun stopRegisterData()

    /**
     * Starts the sensors data measure
     */
    fun startRegisterData()

    /**
     * Remove data stored in sensors
     */
    fun resetDataCount()

}