package com.example.sensors.storage

import com.example.data.datastore.SensorDatastore
import com.example.sensors.Sensor
import com.example.sensors.toDataSensor
import kotlinx.coroutines.flow.map

class SensorStorage(
    private val brightnessSensor: Sensor,
    private val orientationSensor: Sensor,
    private val accelerationSensor: Sensor
): SensorDatastore {

    override fun getBrightnessSensorFlow() = brightnessSensor.sensorFlow.map { it.toDataSensor() }
    override fun getOrientationSensorFlow() = orientationSensor.sensorFlow.map { it.toDataSensor() }
    override fun getAccelerometerSensorFlow() = accelerationSensor.sensorFlow.map { it.toDataSensor() }

    override fun getBrightnessSensorData() = brightnessSensor.getSensorValue().toDataSensor()
    override fun getOrientationSensorData() = orientationSensor.getSensorValue().toDataSensor()
    override fun getAccelerometerSensorData() = accelerationSensor.getSensorValue().toDataSensor()

    override fun stopRegisterData() {
        brightnessSensor.stopRegisterData()
        orientationSensor.stopRegisterData()
        accelerationSensor.stopRegisterData()
    }

    override fun startRegisterData() {
        brightnessSensor.startRegisterData()
        orientationSensor.startRegisterData()
        accelerationSensor.startRegisterData()
    }

    override fun resetDataCount() {
        brightnessSensor.resetDataCount()
        orientationSensor.resetDataCount()
        accelerationSensor.resetDataCount()
    }

}