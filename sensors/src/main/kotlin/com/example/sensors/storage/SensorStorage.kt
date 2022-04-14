package com.example.sensors.storage

import android.hardware.Sensor.*
import android.hardware.SensorManager
import com.example.data.datastore.SensorDatastore
import com.example.data.entity.DataSensor
import com.example.sensors.Sensor
import com.example.sensors.toDataSensor
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SensorStorage(
    private val sensorManager: SensorManager
): SensorDatastore {

    private var brightnessSensor: Sensor? = null
    private var orientationSensor: Sensor? = null
    private var accelerationSensor: Sensor? = null

    override fun initializeBrightnessSensor() {
        if (brightnessSensor == null) {
            brightnessSensor = Sensor(
                sensorManager,
                TYPE_LIGHT
            )
        }
    }

    override fun initializeOrientationSensor() {
        if (orientationSensor == null) {
            orientationSensor = Sensor(
                sensorManager,
                TYPE_ROTATION_VECTOR
            )
        }
    }

    override fun initializeAccelerationSensor() {
        if (accelerationSensor == null) {
            accelerationSensor = Sensor(
                sensorManager,
                TYPE_LINEAR_ACCELERATION
            )
        }
    }

    override fun getBrightnessSensorFlow() = brightnessSensor?.let { sensor ->
        sensor.sensorFlow.map { it.toDataSensor() }
    } ?: flowOf()

    override fun getOrientationSensorFlow() = orientationSensor?.let { sensor ->
        sensor.sensorFlow.map { it.toDataSensor() }
    } ?: flowOf()

    override fun getAccelerometerSensorFlow() = accelerationSensor?.let { sensor ->
        sensor.sensorFlow.map { it.toDataSensor() }
    } ?: flowOf()

    override fun getBrightnessSensorData() = brightnessSensor?.getSensorValue()?.toDataSensor()
        ?: DataSensor()

    override fun getOrientationSensorData() = orientationSensor?.getSensorValue()?.toDataSensor()
        ?: DataSensor()

    override fun getAccelerometerSensorData() = accelerationSensor?.getSensorValue()?.toDataSensor()
        ?: DataSensor()

}