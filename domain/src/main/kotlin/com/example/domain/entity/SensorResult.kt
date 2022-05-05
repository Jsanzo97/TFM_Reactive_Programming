package com.example.domain.entity

data class SensorResult(
    var valueX: Float = 0f,
    var valueY: Float = 0f,
    var valueZ: Float = 0f,
    val sensorType: Int? = null,
    val numberOfDataGenerated: Long = 0L
)

fun SensorResult.reset() {
    valueX = 0f
    valueY = 0f
    valueZ = 0f
}