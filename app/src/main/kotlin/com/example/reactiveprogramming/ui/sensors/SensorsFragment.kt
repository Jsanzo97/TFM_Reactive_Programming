package com.example.reactiveprogramming.ui.sensors

import android.hardware.Sensor
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.reactiveprogramming.R
import com.example.sensors.SensorData
import com.example.sensors.formatToString
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class SensorsFragment: CustomFragment(R.layout.sensors_fragment) {

    private val sensorsViewModel: SensorsViewModel by viewModel()

    private val reactiveBrightnessResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_brightness_result_text) }
    private val reactiveBrightnessMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_brightness_max_result_text) }
    private val reactiveBrightnessMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_brightness_min_result_text) }
    private val reactiveOrientationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_orientation_result_text) }
    private val reactiveOrientationMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_orientation_max_result_text) }
    private val reactiveOrientationMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_orientation_min_result_text) }
    private val reactiveAccelerationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_acceleration_result_text) }
    private val reactiveAccelerationMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_acceleration_max_result_text) }
    private val reactiveAccelerationMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_acceleration_min_result_text) }

    private val functionalBrightnessResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_brightness_result_text) }
    private val functionalBrightnessMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_brightness_max_result_text) }
    private val functionalBrightnessMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_brightness_min_result_text) }
    private val functionalOrientationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_orientation_result_text) }
    private val functionalOrientationMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_orientation_max_result_text) }
    private val functionalOrientationMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_orientation_min_result_text) }
    private val functionalAccelerationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_acceleration_result_text) }
    private val functionalAccelerationMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_acceleration_max_result_text) }
    private val functionalAccelerationMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_acceleration_min_result_text) }

    private val startUpdateSensorDataButton by lazy { requireView().findViewById<MaterialButton>(R.id.start_update_sensor_data_button) }
    private val stopUpdateSensorDataButton by lazy { requireView().findViewById<MaterialButton>(R.id.stop_update_sensor_data_button) }
    private val updateSensorDataButton by lazy { requireView().findViewById<MaterialButton>(R.id.update_sensor_data_button) }
    private val clearSensorDataButton by lazy { requireView().findViewById<MaterialButton>(R.id.clear_sensor_data_button) }

    private var brightnessDataFlow: Job? = null
    private var orientationDataFlow: Job? = null
    private var accelerationDataFlow: Job? = null

    private var reactiveBrightnessMaxValue = SensorData(sensorType = Sensor.TYPE_LIGHT)
    private var reactiveBrightnessMinValue = SensorData(sensorType = Sensor.TYPE_LIGHT)
    private var reactiveOrientationMaxValue = SensorData(sensorType = Sensor.TYPE_ROTATION_VECTOR)
    private var reactiveOrientationMinValue = SensorData(sensorType = Sensor.TYPE_ROTATION_VECTOR)
    private var reactiveAccelerationMaxValue = SensorData(sensorType = Sensor.TYPE_LINEAR_ACCELERATION)
    private var reactiveAccelerationMinValue = SensorData(sensorType = Sensor.TYPE_LINEAR_ACCELERATION)


    override var uiConfigurationViewState = UiConfigurationViewState(
        showToolbar = true,
        statusBarColor = R.color.colorPrimaryDark.some(),
        toolbarColor = R.color.colorPrimary.some(),
        toolbarNavigationIconColor = R.color.white.some(),
        showLogoutButton = true
    )

    override fun handleUiConfigurationViewState(uiConfigurationViewState: UiConfigurationViewState) = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        startUpdateSensorDataButton.setOnClickListener { startUpdateSensorData() }
        stopUpdateSensorDataButton.setOnClickListener { stopUpdateSensorData() }
        updateSensorDataButton.setOnClickListener { updateSensorData() }
        clearSensorDataButton.setOnClickListener { clearSensorData() }
    }

    private fun startUpdateSensorData() {
        brightnessDataFlow = lifecycleScope.launchWhenStarted {
            sensorsViewModel.getBrightSensorFlow().let { flow ->
                flow.cancellable().collectLatest { sensorData ->
                    reactiveBrightnessResultText.text = sensorData.formatToString()
                    checkBrightnessValue(sensorData)
                }
            }
        }

        orientationDataFlow = lifecycleScope.launchWhenStarted {
            sensorsViewModel.getOrientationSensorFlow().let { flow ->
                flow.cancellable().collectLatest { sensorData ->
                    reactiveOrientationResultText.text = sensorData.formatToString()
                    checkOrientationValue(sensorData)
                }
            }
        }

        accelerationDataFlow = lifecycleScope.launchWhenStarted {
            sensorsViewModel.getAccelerometerSensorFlow().let { flow ->
                flow.cancellable().collectLatest { sensorData ->
                    reactiveAccelerationResultText.text = sensorData.formatToString()
                    checkAccelerationValue(sensorData)
                }
            }
        }
    }

    private fun checkBrightnessValue(sensorData: SensorData) {
        if (sensorData.valueX > reactiveBrightnessMaxValue.valueX) {
            reactiveBrightnessMaxValue.valueX = sensorData.valueX
            reactiveBrightnessMaxResultText.text = reactiveBrightnessMaxValue.formatToString()
        }

        if (sensorData.valueX < reactiveBrightnessMinValue.valueX) {
            reactiveBrightnessMinValue.valueX = sensorData.valueX
            reactiveBrightnessMinResultText.text = reactiveBrightnessMinValue.formatToString()
        }

        if (reactiveBrightnessMinValue.valueX == 0f) {
            reactiveBrightnessMinValue.valueX = sensorData.valueX
        }

    }

    private fun checkOrientationValue(sensorData: SensorData) {
        if (sensorData.valueX > reactiveOrientationMaxValue.valueX) {
            reactiveOrientationMaxValue.valueX = sensorData .valueX
            reactiveOrientationMaxResultText.text = reactiveOrientationMaxValue.formatToString()
        }

        if (sensorData.valueX < reactiveOrientationMinValue.valueX) {
            reactiveOrientationMinValue.valueX = sensorData.valueX
            reactiveOrientationMinResultText.text = reactiveOrientationMinValue.formatToString()
        }

        if (sensorData.valueY > reactiveOrientationMaxValue.valueY) {
            reactiveOrientationMaxValue.valueY = sensorData .valueY
            reactiveOrientationMaxResultText.text = reactiveOrientationMaxValue.formatToString()
        }

        if (sensorData.valueY < reactiveOrientationMinValue.valueY) {
            reactiveOrientationMinValue.valueY = sensorData.valueY
            reactiveOrientationMinResultText.text = reactiveOrientationMinValue.formatToString()
        }

        if (sensorData.valueZ > reactiveOrientationMaxValue.valueZ) {
            reactiveOrientationMaxValue.valueZ = sensorData .valueZ
            reactiveOrientationMaxResultText.text = reactiveOrientationMaxValue.formatToString()
        }

        if (sensorData.valueZ < reactiveOrientationMinValue.valueZ) {
            reactiveOrientationMinValue.valueZ = sensorData.valueZ
            reactiveOrientationMinResultText.text = reactiveOrientationMinValue.formatToString()
        }
    }

    private fun checkAccelerationValue(sensorData: SensorData) {
        if (sensorData.valueX > reactiveAccelerationMaxValue.valueX) {
            reactiveAccelerationMaxValue.valueX = sensorData .valueX
            reactiveAccelerationMaxResultText.text = reactiveAccelerationMaxValue.formatToString()
        }

        if (sensorData.valueX < reactiveAccelerationMinValue.valueX) {
            reactiveAccelerationMinValue.valueX = sensorData.valueX
            reactiveAccelerationMinResultText.text = reactiveAccelerationMinValue.formatToString()
        }

        if (sensorData.valueY > reactiveAccelerationMaxValue.valueY) {
            reactiveAccelerationMaxValue.valueY = sensorData .valueY
            reactiveAccelerationMaxResultText.text = reactiveAccelerationMaxValue.formatToString()
        }

        if (sensorData.valueY < reactiveAccelerationMinValue.valueY) {
            reactiveAccelerationMinValue.valueY = sensorData.valueY
            reactiveAccelerationMinResultText.text = reactiveAccelerationMinValue.formatToString()
        }

        if (sensorData.valueZ > reactiveAccelerationMaxValue.valueZ) {
            reactiveAccelerationMaxValue.valueZ = sensorData .valueZ
            reactiveAccelerationMaxResultText.text = reactiveAccelerationMaxValue.formatToString()
        }

        if (sensorData.valueZ < reactiveAccelerationMinValue.valueZ) {
            reactiveAccelerationMinValue.valueZ = sensorData.valueZ
            reactiveAccelerationMinResultText.text = reactiveAccelerationMinValue.formatToString()
        }
    }

    private fun stopUpdateSensorData() {
        brightnessDataFlow?.cancel()
        orientationDataFlow?.cancel()
        accelerationDataFlow?.cancel()
    }

    private fun updateSensorData() {
        reactiveBrightnessResultText.text = sensorsViewModel.getBrightnessSensorData().formatToString()
        reactiveOrientationResultText.text = sensorsViewModel.getOrientationSensorData().formatToString()
        reactiveAccelerationResultText.text = sensorsViewModel.getAccelerometerSensorData().formatToString()
        functionalBrightnessResultText.text = sensorsViewModel.getBrightnessSensorData().formatToString()
        functionalOrientationResultText.text = sensorsViewModel.getOrientationSensorData().formatToString()
        functionalAccelerationResultText.text = sensorsViewModel.getAccelerometerSensorData().formatToString()
    }

    private fun clearSensorData() {
        stopUpdateSensorData()

        reactiveBrightnessResultText.text = null
        reactiveOrientationResultText.text = null
        reactiveAccelerationResultText.text = null

        reactiveBrightnessMaxValue.valueX = 0f
        reactiveBrightnessMinValue.valueX = 0f
        reactiveBrightnessMaxResultText.text = null
        reactiveBrightnessMinResultText.text = null

        reactiveOrientationMaxValue.valueX = 0f
        reactiveOrientationMaxValue.valueY = 0f
        reactiveOrientationMaxValue.valueZ = 0f
        reactiveOrientationMinValue.valueX = 0f
        reactiveOrientationMinValue.valueY = 0f
        reactiveOrientationMinValue.valueZ = 0f
        reactiveOrientationMaxResultText.text = null
        reactiveOrientationMinResultText.text = null

        reactiveAccelerationMaxValue.valueX = 0f
        reactiveAccelerationMaxValue.valueY = 0f
        reactiveAccelerationMaxValue.valueZ = 0f
        reactiveAccelerationMinValue.valueX = 0f
        reactiveAccelerationMinValue.valueY = 0f
        reactiveAccelerationMinValue.valueZ = 0f
        reactiveAccelerationMaxResultText.text = null
        reactiveAccelerationMinResultText.text = null

        functionalBrightnessResultText.text = null
        functionalOrientationResultText.text = null
        functionalAccelerationResultText.text = null
    }
}