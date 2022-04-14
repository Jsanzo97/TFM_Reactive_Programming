package com.example.reactiveprogramming.ui.sensors

import android.content.Context
import android.hardware.Sensor.*
import androidx.lifecycle.ViewModel
import com.example.common.EMPTY_STRING
import com.example.domain.entity.SensorResult
import com.example.domain.usecase.*
import com.example.reactiveprogramming.R

class SensorsViewModel(
    private val getBrightnessSensorFlowUseCase: GetBrightnessSensorFlowUseCase,
    private val getOrientationSensorFlowUseCase: GetOrientationSensorFlowUseCase,
    private val getAccelerometerSensorFlowUseCase: GetAccelerometerSensorFlowUseCase,
    private val getBrightnessSensorDataUseCase: GetBrightnessSensorDataUseCase,
    private val getOrientationSensorDataUseCase: GetOrientationSensorDataUseCase,
    private val getAccelerometerSensorDataUseCase: GetAccelerometerSensorDataUseCase
): ViewModel() {

    fun getBrightSensorFlow() = getBrightnessSensorFlowUseCase()
    fun getOrientationSensorFlow() = getOrientationSensorFlowUseCase()
    fun getAccelerometerSensorFlow() = getAccelerometerSensorFlowUseCase()

    fun getBrightnessSensorData() = getBrightnessSensorDataUseCase()
    fun getOrientationSensorData() = getOrientationSensorDataUseCase()
    fun getAccelerometerSensorData() = getAccelerometerSensorDataUseCase()

}

fun SensorResult.formatToString(context: Context): String {
    return when (sensorType) {
        TYPE_LIGHT -> {
            context.getString(
                R.string.brightness_value,
                String.format("%.2f", valueX)
            )
        }
        TYPE_ROTATION_VECTOR -> {
            context.getString(
                R.string.orientation_value,
                String.format("%.2f", valueX),
                String.format("%.2f", valueY),
                String.format("%.2f", valueZ)
            )
        }
        TYPE_LINEAR_ACCELERATION -> {
            context.getString(
                R.string.acceleration_value,
                String.format("%.2f", valueX),
                String.format("%.2f", valueY),
                String.format("%.2f", valueZ)
            )
        }
        else -> { EMPTY_STRING }
    }
}