package com.example.reactiveprogramming.ui.sensors

import android.content.Context
import android.hardware.Sensor.*
import androidx.lifecycle.ViewModel
import com.example.common.EMPTY_STRING
import com.example.domain.entity.SensorResult
import com.example.domain.usecase.*
import com.example.reactiveprogramming.R

/**
 * ViewModel to manage the actions performed in the SensorsFragment
 * @param getBrightnessSensorFlowUseCase connect with the flow that emit the brightness sensor data
 * @param getOrientationSensorFlowUseCase connect with the flow that emit the orientation sensor data
 * @param getAccelerometerSensorFlowUseCase connect with the flow that emit the acceleration sensor data
 * @param getBrightnessSensorDataUseCase retrieve the brightness sensor data at this moment
 * @param getOrientationSensorDataUseCase retrieve the orientation sensor data at this moment
 * @param getAccelerometerSensorDataUseCase retrieve the orientation sensor data at this moment
 * @param startMeasureSensorDataUseCase start the data collection in the sensors
 * @param stopMeasureSensorDataUseCase stop the data collection in the sensors
 * @param resetMeasureSensorDataUseCase reset the data measured by sensors
 */
class SensorsViewModel(
    private val getBrightnessSensorFlowUseCase: GetBrightnessSensorFlowUseCase,
    private val getOrientationSensorFlowUseCase: GetOrientationSensorFlowUseCase,
    private val getAccelerometerSensorFlowUseCase: GetAccelerometerSensorFlowUseCase,
    private val getBrightnessSensorDataUseCase: GetBrightnessSensorDataUseCase,
    private val getOrientationSensorDataUseCase: GetOrientationSensorDataUseCase,
    private val getAccelerometerSensorDataUseCase: GetAccelerometerSensorDataUseCase,
    private val startMeasureSensorDataUseCase: StartMeasureSensorDataUseCase,
    private val stopMeasureSensorDataUseCase: StopMeasureSensorDataUseCase,
    private val resetMeasureSensorDataUseCase: ResetMeasureSensorDataUseCase
): ViewModel() {

    // Each function execute its use case

    fun getBrightSensorFlow() = getBrightnessSensorFlowUseCase()
    fun getOrientationSensorFlow() = getOrientationSensorFlowUseCase()
    fun getAccelerometerSensorFlow() = getAccelerometerSensorFlowUseCase()

    fun getBrightnessSensorData() = getBrightnessSensorDataUseCase()
    fun getOrientationSensorData() = getOrientationSensorDataUseCase()
    fun getAccelerometerSensorData() = getAccelerometerSensorDataUseCase()

    fun startMeasureSensorData() = startMeasureSensorDataUseCase()
    fun stopMeasureSensorData() = stopMeasureSensorDataUseCase()
    fun resetMeasureSensorData() = resetMeasureSensorDataUseCase()

}

/**
 * Format the raw data of sensor to friendly string depending of the sensor type
 */
fun SensorResult.formatToString(context: Context): String {
    return when (sensorType) {
        TYPE_LIGHT -> {
            context.getString(
                R.string.sensor_value,
                String.format("%.2f", valueX)
            )
        }
        TYPE_ROTATION_VECTOR -> {
            context.getString(
                R.string.sensor_3d_value,
                String.format("%.2f", valueX),
                String.format("%.2f", valueY),
                String.format("%.2f", valueZ)
            )
        }
        TYPE_LINEAR_ACCELERATION -> {
            context.getString(
                R.string.sensor_3d_value,
                String.format("%.2f", valueX),
                String.format("%.2f", valueY),
                String.format("%.2f", valueZ)
            )
        }
        else -> { EMPTY_STRING }
    }
}