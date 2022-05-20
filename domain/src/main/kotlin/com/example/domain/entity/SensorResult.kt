package com.example.domain.entity

/**
 * Represent data recovered from sensor
 * @param valueX value of the sensor on X axis
 * @param valueY value of the sensor on Y axis
 * @param valueZ value of the sensor on Z axis
 * @param sensorType type of the sensor, it can be Brightness, Orientation or Acceleration
 * @param numberOfDataGenerated number of single data measured by the sensor
 */
data class SensorResult(
    var valueX: Float = 0f,
    var valueY: Float = 0f,
    var valueZ: Float = 0f,
    val sensorType: Int? = null,
    val numberOfDataGenerated: Long = 0L
)

/**
 * Reset the sensor data stored
 */
fun SensorResult.reset() {
    valueX = 0f
    valueY = 0f
    valueZ = 0f
}