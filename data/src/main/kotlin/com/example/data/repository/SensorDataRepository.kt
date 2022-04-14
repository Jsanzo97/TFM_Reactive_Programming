package com.example.data.repository

import com.example.data.datastore.SensorDatastore
import com.example.data.entity.toSensorResult
import com.example.domain.repository.SensorRepository
import kotlinx.coroutines.flow.map

class SensorDataRepository (
    private val sensorDatastore: SensorDatastore
) : SensorRepository {

    override fun initializeBrightnessSensor() = sensorDatastore.initializeBrightnessSensor()
    override fun initializeOrientationSensor() = sensorDatastore.initializeOrientationSensor()
    override fun initializeAccelerationSensor() = sensorDatastore.initializeAccelerationSensor()


    override fun getBrightnessSensorFlow() = sensorDatastore.getBrightnessSensorFlow().map { it.toSensorResult() }
    override fun getOrientationSensorFlow() = sensorDatastore.getOrientationSensorFlow().map { it.toSensorResult() }
    override fun getAccelerometerSensorFlow() = sensorDatastore.getAccelerometerSensorFlow().map { it.toSensorResult() }

    override fun getBrightnessSensorData() = sensorDatastore.getBrightnessSensorData().toSensorResult()
    override fun getOrientationSensorData() = sensorDatastore.getOrientationSensorData().toSensorResult()
    override fun getAccelerometerSensorData() = sensorDatastore.getAccelerometerSensorData().toSensorResult()

}