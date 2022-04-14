package com.example.data.entity

import com.example.domain.entity.SensorResult

data class DataSensor(
    var valueX: Float = 0f,
    var valueY: Float = 0f,
    var valueZ: Float = 0f,
    val sensorType: Int? = null
)

fun DataSensor.toSensorResult() = SensorResult(
    valueX, valueY, valueZ, sensorType
)