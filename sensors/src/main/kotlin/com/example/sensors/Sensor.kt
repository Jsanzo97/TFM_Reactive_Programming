package com.example.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.updateAndGet

class Sensor(sensorManager: SensorManager, sensorType: Int) {

    private var sensor: Sensor? = null

    private var sensorValue = floatArrayOf()

    private var _sensorFlow = MutableStateFlow(SensorData())
    val sensorFlow: Flow<SensorData> get() = _sensorFlow

    init {
        sensor = sensorManager.getDefaultSensor(sensorType)

        sensorManager.registerListener(object: SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == sensorType) {
                    sensorValue = event.values
                    _sensorFlow.value = SensorData(
                        event.values[0],
                        event.values[1],
                        event.values[2],
                        sensorType
                    )

                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

        }, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun getBrightnessValue() = sensorValue
}