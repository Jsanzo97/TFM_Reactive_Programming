package com.example.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class Sensor(sensorManager: SensorManager, sensorType: Int) {

    private var sensor: Sensor? = null

    private var sensorValue = SensorDataType(sensorType = sensorType)

    private var _sensorFlow = MutableStateFlow(SensorDataType())
    val sensorFlow: Flow<SensorDataType> get() = _sensorFlow

    var numberOfDataGenerated = 0L
    var shouldCountData = false

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

    fun stopRegisterData() {
        shouldCountData = false
    }

    fun startRegisterData() {
        shouldCountData = true
        numberOfDataGenerated++
    }

    fun resetDataCount() {
        stopRegisterData()
        numberOfDataGenerated = 0L
    }

    fun getSensorValue() = sensorValue
}