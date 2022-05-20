package com.example.data.entity

import com.example.domain.entity.SensorResult

/**
 * Represent data recovered from sensor
 * @param valueX value of the sensor on X axis
 * @param valueY value of the sensor on Y axis
 * @param valueZ value of the sensor on Z axis
 * @param sensorType type of the sensor, it can be Brightness, Orientation or Acceleration
 * @param numberOfDataGenerated number of single data measured by the sensor
 */
data class DataSensor(
    var valueX: Float = 0f,
    var valueY: Float = 0f,
    var valueZ: Float = 0f,
    val sensorType: Int? = null,
    val numberOfDataGenerated: Long = 0L
)

/**
 * Convert DataSensor to SensorResult
 * @return new SensorResult with the DataSensor values
 */
fun DataSensor.toSensorResult() = SensorResult(
    valueX, valueY, valueZ, sensorType, numberOfDataGenerated
)