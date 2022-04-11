package com.example.reactiveprogramming.ui.sensors

import androidx.lifecycle.ViewModel
import com.example.sensors.Sensor

class SensorsViewModel(
    private val brightnessSensor: Sensor,
    private val orientationSensor: Sensor,
    private val accelerometerSensor: Sensor
): ViewModel() {

    fun getBrightSensorFlow() = brightnessSensor.sensorFlow
    fun getOrientationSensorFlow() = orientationSensor.sensorFlow
    fun getAccelerometerSensorFlow() = accelerometerSensor.sensorFlow

    fun getBrightnessSensorData() = brightnessSensor.getSensorValue()
    fun getOrientationSensorData() = orientationSensor.getSensorValue()
    fun getAccelerometerSensorData() = accelerometerSensor.getSensorValue()

}