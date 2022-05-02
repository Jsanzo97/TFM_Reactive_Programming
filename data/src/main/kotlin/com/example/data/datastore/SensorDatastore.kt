package com.example.data.datastore

import com.example.data.entity.DataSensor
import kotlinx.coroutines.flow.Flow

interface SensorDatastore {

    fun getBrightnessSensorFlow(): Flow<DataSensor>
    fun getOrientationSensorFlow(): Flow<DataSensor>
    fun getAccelerometerSensorFlow(): Flow<DataSensor>

    fun getBrightnessSensorData(): DataSensor
    fun getOrientationSensorData(): DataSensor
    fun getAccelerometerSensorData(): DataSensor

    fun stopRegisterData()
    fun startRegisterData()
    fun resetDataCount()

}