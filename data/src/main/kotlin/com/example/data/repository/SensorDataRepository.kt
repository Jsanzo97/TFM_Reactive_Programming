package com.example.data.repository

import com.example.data.datastore.SensorDatastore
import com.example.data.entity.toSensorResult
import com.example.domain.repository.CollectionsRepository
import com.example.domain.repository.SensorRepository
import kotlinx.coroutines.flow.map

/**
 * Implementation of SensorRepository to communicate the Domain layer with the Data layer
 * @see SensorRepository
 */
class SensorDataRepository (
    private val sensorDatastore: SensorDatastore
) : SensorRepository {

    override fun getBrightnessSensorFlow() = sensorDatastore.getBrightnessSensorFlow().map { it.toSensorResult() }
    override fun getOrientationSensorFlow() = sensorDatastore.getOrientationSensorFlow().map { it.toSensorResult() }
    override fun getAccelerometerSensorFlow() = sensorDatastore.getAccelerometerSensorFlow().map { it.toSensorResult() }

    override fun getBrightnessSensorData() = sensorDatastore.getBrightnessSensorData().toSensorResult()
    override fun getOrientationSensorData() = sensorDatastore.getOrientationSensorData().toSensorResult()
    override fun getAccelerometerSensorData() = sensorDatastore.getAccelerometerSensorData().toSensorResult()

    override fun stopRegisterData() = sensorDatastore.stopRegisterData()
    override fun startRegisterData() = sensorDatastore.startRegisterData()
    override fun resetDataCount() = sensorDatastore.resetDataCount()

}