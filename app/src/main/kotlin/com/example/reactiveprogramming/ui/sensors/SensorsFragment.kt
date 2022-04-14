package com.example.reactiveprogramming.ui.sensors

import android.hardware.Sensor.*
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.domain.entity.SensorResult
import com.example.reactiveprogramming.R
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

    private var reactiveBrightnessMaxValue = SensorResult(sensorType = TYPE_LIGHT)
    private var reactiveBrightnessMinValue = SensorResult(sensorType = TYPE_LIGHT)
    private var reactiveOrientationMaxValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var reactiveOrientationMinValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var reactiveAccelerationMaxValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var reactiveAccelerationMinValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)

    private var functionalBrightnessMaxValue = SensorResult(sensorType = TYPE_LIGHT)
    private var functionalBrightnessMinValue = SensorResult(sensorType = TYPE_LIGHT)
    private var functionalOrientationMaxValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var functionalOrientationMinValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var functionalAccelerationMaxValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var functionalAccelerationMinValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)


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
            startUpdateSensorDataButton.isEnabled = false
            sensorsViewModel.getBrightSensorFlow().let { flow ->
                flow.cancellable().collectLatest { sensorData ->
                    reactiveBrightnessResultText.text = sensorData.formatToString(requireContext())
                    checkSensorValue(
                        sensorData,
                        reactiveBrightnessMaxValue,
                        reactiveBrightnessMinValue,
                        reactiveBrightnessMaxResultText,
                        reactiveBrightnessMinResultText
                    )
                }
            }
        }

        orientationDataFlow = lifecycleScope.launchWhenStarted {
            sensorsViewModel.getOrientationSensorFlow().let { flow ->
                flow.cancellable().collectLatest { sensorData ->
                    reactiveOrientationResultText.text = sensorData.formatToString(requireContext())
                    checkSensorValue(
                        sensorData,
                        reactiveOrientationMaxValue,
                        reactiveOrientationMinValue,
                        reactiveOrientationMaxResultText,
                        reactiveOrientationMinResultText
                    )
                }
            }
        }

        accelerationDataFlow = lifecycleScope.launchWhenStarted {
            sensorsViewModel.getAccelerometerSensorFlow().let { flow ->
                flow.cancellable().collectLatest { sensorData ->
                    reactiveAccelerationResultText.text = sensorData.formatToString(requireContext())
                    checkSensorValue(
                        sensorData,
                        reactiveAccelerationMaxValue,
                        reactiveAccelerationMinValue,
                        reactiveAccelerationMaxResultText,
                        reactiveAccelerationMinResultText
                    )
                }
            }
        }
    }

    private fun checkSensorValue(
        sensorData: SensorResult,
        maxValueToCompareWith: SensorResult,
        minValueToCompareWith: SensorResult,
        maxTextToUpdate: MaterialTextView,
        minTextToUpdate: MaterialTextView

    ) {
        if (sensorData.sensorType == TYPE_LIGHT && minValueToCompareWith.valueX == 0f) {
            minValueToCompareWith.valueX = sensorData.valueX
        }

        maxValueToCompareWith.valueX = maxOf(maxValueToCompareWith.valueX, sensorData.valueX)
        minValueToCompareWith.valueX = minOf(minValueToCompareWith.valueX, sensorData.valueX)

        maxValueToCompareWith.valueY = maxOf(maxValueToCompareWith.valueY, sensorData.valueY)
        minValueToCompareWith.valueY = minOf(minValueToCompareWith.valueY, sensorData.valueY)

        maxValueToCompareWith.valueZ = maxOf(maxValueToCompareWith.valueZ, sensorData.valueZ)
        minValueToCompareWith.valueZ = minOf(minValueToCompareWith.valueZ, sensorData.valueZ)

        maxTextToUpdate.text = getString(R.string.max_sensor_value, maxValueToCompareWith.formatToString(requireContext()))
        minTextToUpdate.text = getString(R.string.min_sensor_value, minValueToCompareWith.formatToString(requireContext()))
    }

    private fun stopUpdateSensorData() {
        brightnessDataFlow?.cancel()
        orientationDataFlow?.cancel()
        accelerationDataFlow?.cancel()
        startUpdateSensorDataButton.isEnabled = true
    }

    private fun updateSensorData() {
        reactiveBrightnessResultText.text = sensorsViewModel.getBrightnessSensorData().formatToString(requireContext())
        reactiveOrientationResultText.text = sensorsViewModel.getOrientationSensorData().formatToString(requireContext())
        reactiveAccelerationResultText.text = sensorsViewModel.getAccelerometerSensorData().formatToString(requireContext())

        functionalBrightnessResultText.text = sensorsViewModel.getBrightnessSensorData().formatToString(requireContext())
        functionalOrientationResultText.text = sensorsViewModel.getOrientationSensorData().formatToString(requireContext())
        functionalAccelerationResultText.text = sensorsViewModel.getAccelerometerSensorData().formatToString(requireContext())

        checkSensorValue(
            sensorsViewModel.getBrightnessSensorData(),
            reactiveBrightnessMaxValue,
            reactiveBrightnessMinValue,
            reactiveBrightnessMaxResultText,
            reactiveBrightnessMinResultText
        )

        checkSensorValue(
            sensorsViewModel.getOrientationSensorData(),
            reactiveOrientationMaxValue,
            reactiveOrientationMinValue,
            reactiveOrientationMaxResultText,
            reactiveOrientationMinResultText
        )

        checkSensorValue(
            sensorsViewModel.getAccelerometerSensorData(),
            reactiveAccelerationMaxValue,
            reactiveAccelerationMinValue,
            reactiveAccelerationMaxResultText,
            reactiveAccelerationMinResultText
        )

        checkSensorValue(
            sensorsViewModel.getBrightnessSensorData(),
            functionalBrightnessMaxValue,
            functionalBrightnessMinValue,
            functionalBrightnessMaxResultText,
            functionalBrightnessMinResultText
        )

        checkSensorValue(
            sensorsViewModel.getOrientationSensorData(),
            functionalOrientationMaxValue,
            functionalOrientationMinValue,
            functionalOrientationMaxResultText,
            functionalOrientationMinResultText
        )

        checkSensorValue(
            sensorsViewModel.getAccelerometerSensorData(),
            functionalAccelerationMaxValue,
            functionalAccelerationMinValue,
            functionalAccelerationMaxResultText,
            functionalAccelerationMinResultText
        )
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

        functionalAccelerationMaxResultText.text = null
        functionalAccelerationMinResultText.text = null

        functionalBrightnessResultText.text = null
        functionalOrientationResultText.text = null
        functionalAccelerationResultText.text = null

        functionalBrightnessMaxValue.valueX = 0f
        functionalBrightnessMinValue.valueX = 0f

        functionalBrightnessMaxResultText.text = null
        functionalBrightnessMinResultText.text = null

        functionalOrientationMaxValue.valueX = 0f
        functionalOrientationMaxValue.valueY = 0f
        functionalOrientationMaxValue.valueZ = 0f
        functionalOrientationMinValue.valueX = 0f
        functionalOrientationMinValue.valueY = 0f
        functionalOrientationMinValue.valueZ = 0f

        functionalOrientationMaxResultText.text = null
        functionalOrientationMinResultText.text = null

        functionalAccelerationMaxValue.valueX = 0f
        functionalAccelerationMaxValue.valueY = 0f
        functionalAccelerationMaxValue.valueZ = 0f
        functionalAccelerationMinValue.valueX = 0f
        functionalAccelerationMinValue.valueY = 0f
        functionalAccelerationMinValue.valueZ = 0f

        functionalAccelerationMaxResultText.text = null
        functionalAccelerationMinResultText.text = null
    }
}