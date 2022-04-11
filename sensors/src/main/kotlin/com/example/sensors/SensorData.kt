package com.example.sensors

import android.hardware.Sensor.*

data class SensorData(
    var valueX: Float = 0f,
    var valueY: Float = 0f,
    var valueZ: Float = 0f,
    val sensorType: Int? = null
)

fun SensorData.formatToString(): String {
    return when (sensorType) {
        TYPE_LIGHT -> {
            "Brightness value ${String.format("%.2f", valueX)}"
        }
        TYPE_ROTATION_VECTOR -> { "Rotation values " +
                "X: ${String.format("%.2f", valueX)} " +
                "Y: ${String.format("%.2f", valueY)} " +
                "Z: ${String.format("%.2f", valueZ)}"
        }
        TYPE_LINEAR_ACCELERATION -> { "Acceleration values " +
                "X: ${String.format("%.2f", valueX)} " +
                "Y: ${String.format("%.2f", valueY)} " +
                "Z: ${String.format("%.2f", valueZ)}"
        }
        else -> {""}
    }
}