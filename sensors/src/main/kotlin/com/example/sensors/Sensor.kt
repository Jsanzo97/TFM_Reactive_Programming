package com.example.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Manages the measure of data by the sensor
 * @param sensorManager system sensor manager necessary to initialize this custom sensor
 * @param sensorType type of the sensor that we want to create, it can be Brightness, Acceleration or Orientation
 */
class Sensor(sensorManager: SensorManager, sensorType: Int) {

    private var sensor: Sensor? = null

    private var sensorValue = SensorDataType(sensorType = sensorType)

    private var _sensorFlow = MutableStateFlow(SensorDataType())
    val sensorFlow: Flow<SensorDataType> get() = _sensorFlow

    var numberOfDataGenerated = 0L
    var shouldCountData = false

    /**
     * Initialization of the sensor and registration of its listener
     */
    init {
        sensor = sensorManager.getDefaultSensor(sensorType)

        sensorManager.registerListener(
            object: SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    if (event.sensor.type == sensorType) {
                        if (shouldCountData) numberOfDataGenerated++
                        sensorValue =  SensorDataType(
                            event.values[0],
                            event.values[1],
                            event.values[2],
                            sensorType,
                            numberOfDataGenerated
                        )
                        _sensorFlow.value = sensorValue
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) { /* no-op */ }
            },
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    /**
     * Stops the measure of data
     */
    fun stopRegisterData() {
        shouldCountData = false
    }

    /**
     * Starts the measure of data
     */
    fun startRegisterData() {
        shouldCountData = true
        numberOfDataGenerated++
    }

    /**
     * Resets the data of the sensor
     */
    fun resetDataCount() {
        stopRegisterData()
        numberOfDataGenerated = 0L
    }

    /**
     * Returns the value of the sensor data at this moment
     * @return value of sensor data
     */
    fun getSensorValue() = sensorValue
}