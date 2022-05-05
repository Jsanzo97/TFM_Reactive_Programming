package com.example.domain.repository

import com.example.domain.entity.SensorResult
import kotlinx.coroutines.flow.Flow

interface SensorRepository {

    fun getBrightnessSensorFlow(): Flow<SensorResult>
    fun getOrientationSensorFlow(): Flow<SensorResult>
    fun getAccelerometerSensorFlow(): Flow<SensorResult>

    fun getBrightnessSensorData(): SensorResult
    fun getOrientationSensorData(): SensorResult
    fun getAccelerometerSensorData(): SensorResult

    fun stopRegisterData()
    fun startRegisterData()
    fun resetDataCount()

}