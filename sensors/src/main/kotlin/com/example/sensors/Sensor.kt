package com.example.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class Sensor(sensorManager: SensorManager, sensorType: Int) {

    private var sensor: Sensor? = null

    private var sensorValue = SensorData()

    private var _sensorFlow = MutableStateFlow(SensorData())
    val sensorFlow: Flow<SensorData> get() = _sensorFlow

    init {
        sensor = sensorManager.getDefaultSensor(sensorType)

        sensorManager.registerListener(object: SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == sensorType) {
                    sensorValue =  SensorData(
                        event.values[0],
                        event.values[1],
                        event.values[2],
                        sensorType
                    )
                    _sensorFlow.value = sensorValue
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

        }, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun getSensorValue() = sensorValue
}