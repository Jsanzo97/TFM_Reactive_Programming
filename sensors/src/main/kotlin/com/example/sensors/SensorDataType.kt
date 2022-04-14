package com.example.sensors

import com.example.data.entity.DataSensor


data class SensorDataType(
    var valueX: Float = 0f,
    var valueY: Float = 0f,
    var valueZ: Float = 0f,
    val sensorType: Int? = null
)

fun SensorDataType.toDataSensor() = DataSensor(
    valueX, valueY, valueZ, sensorType
)