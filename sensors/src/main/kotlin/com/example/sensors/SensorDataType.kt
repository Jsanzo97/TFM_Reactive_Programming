package com.example.sensors

import com.example.data.entity.DataSensor

/**
 * Represent the data that sensor returns
 * @param valueX value of the sensor in the X axis
 * @param valueY value of the sensor in the Y axis
 * @param valueZ value of the sensor in the Z axis
 * @param sensorType type of the sensor, it can be Brightness, Acceleration or Orientation
 * @param numberOfDataGenerated quantity of data measured by sensor
 */
data class SensorDataType(
    var valueX: Float = 0f,
    var valueY: Float = 0f,
    var valueZ: Float = 0f,
    val sensorType: Int? = null,
    val numberOfDataGenerated: Long = 0L
)

/**
 * Convert SensorDataType in DataSensor
 * @return new DataSensor with the SensorDataType values
 */
fun SensorDataType.toDataSensor() = DataSensor(
    valueX, valueY, valueZ, sensorType, numberOfDataGenerated
)