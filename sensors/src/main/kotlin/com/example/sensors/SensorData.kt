package com.example.sensors

import android.hardware.Sensor.*

data class SensorData(
    val valueX: Float? = null,
    val valueY: Float? = null,
    val valueZ: Float? = null,
    val sensorType: Int? = null
)

fun SensorData.formatToString(): String {
    return when (sensorType) {
        TYPE_LIGHT -> { "Brightness value is $valueX" }
        TYPE_ROTATION_VECTOR -> { "Rotation values are X: $valueX Y: $valueY Z: $valueZ" }
        TYPE_LINEAR_ACCELERATION -> { "Acceleration values are X: $valueX Y: $valueY Z: $valueZ" }
        else -> {""}
    }
}