package com.example.data.datastore

import com.example.data.entity.DataSensor
import kotlinx.coroutines.flow.Flow

/**
 * Definition of the functions in the Data layer that we need to implement in the next layer
 */
interface SensorDatastore {

    /**
     * Returns the brightness sensor data flow
     * @return flow with brightness sensor data
     */
    fun getBrightnessSensorFlow(): Flow<DataSensor>

    /**
     * Returns the orientation sensor data flow
     * @return flow with orientation sensor data
     */
    fun getOrientationSensorFlow(): Flow<DataSensor>

    /**
     * Returns the acceleration sensor data flow
     * @return flow with acceleration sensor data
     */
    fun getAccelerometerSensorFlow(): Flow<DataSensor>

    /**
     * Returns the brightness sensor data in this moment
     * @return brightness sensor data in this moment
     */
    fun getBrightnessSensorData(): DataSensor

    /**
     * Returns the orientation sensor data in this moment
     * @return brightness orientation data in this moment
     */
    fun getOrientationSensorData(): DataSensor

    /**
     * Returns the acceleration sensor data in this moment
     * @return acceleration sensor data in this moment
     */
    fun getAccelerometerSensorData(): DataSensor

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