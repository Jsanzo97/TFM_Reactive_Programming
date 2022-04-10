package com.example.reactiveprogramming.ui.sensors

import androidx.lifecycle.ViewModel
import com.example.sensors.Sensor

class SensorsViewModel(
    private val brightnessSensor: Sensor,
    private val orientationSensor: Sensor,
    private val accelerometerSensor: Sensor
): ViewModel() {

    val brightSensorFlow = brightnessSensor.sensorFlow
    val orientationSensorFlow = orientationSensor.sensorFlow
    val accelerometerSensorFlow = accelerometerSensor.sensorFlow

}