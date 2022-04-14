package com.example.sensors.storage

import android.hardware.Sensor.*
import android.hardware.SensorManager
import com.example.data.datastore.SensorDatastore
import com.example.sensors.Sensor
import com.example.sensors.toDataSensor
import kotlinx.coroutines.flow.map

class SensorStorage(
    private val sensorManager: SensorManager
): SensorDatastore {

    override fun getBrightnessSensorFlow() = Sensor(
        sensorManager,
        TYPE_LIGHT
    ).sensorFlow.map { it.toDataSensor() }

    override fun getOrientationSensorFlow() = Sensor(
        sensorManager,
        TYPE_ROTATION_VECTOR
    ).sensorFlow.map { it.toDataSensor() }

    override fun getAccelerometerSensorFlow() = Sensor(
        sensorManager,
        TYPE_LINEAR_ACCELERATION
    ).sensorFlow.map { it.toDataSensor() }

    override fun getBrightnessSensorData() = Sensor(
        sensorManager,
        TYPE_LIGHT
    ).getSensorValue().toDataSensor()

    override fun getOrientationSensorData() = Sensor(
        sensorManager,
        TYPE_ROTATION_VECTOR
    ).getSensorValue().toDataSensor()

    override fun getAccelerometerSensorData() = Sensor(
        sensorManager,
        TYPE_LINEAR_ACCELERATION
    ).getSensorValue().toDataSensor()

}