package com.example.reactiveprogramming.ui.sensors

import android.hardware.Sensor.*
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.domain.entity.SensorResult
import com.example.domain.entity.reset
import com.example.reactiveprogramming.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.*
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
    private val reactiveDataCollectedText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_data_collected_value_text) }
    private val reactiveDataGeneratedText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_data_generated_value_text) }
    private val reactiveDataPerformanceText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_data_performance_text_value) }

    private val functionalBrightnessResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_brightness_result_text) }
    private val functionalBrightnessMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_brightness_max_result_text) }
    private val functionalBrightnessMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_brightness_min_result_text) }
    private val functionalOrientationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_orientation_result_text) }
    private val functionalOrientationMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_orientation_max_result_text) }
    private val functionalOrientationMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_orientation_min_result_text) }
    private val functionalAccelerationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_acceleration_result_text) }
    private val functionalAccelerationMaxResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_acceleration_max_result_text) }
    private val functionalAccelerationMinResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_acceleration_min_result_text) }
    private val functionalDataCollectedText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_data_collected_value_text) }
    private val functionalDataGeneratedText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_data_generated_value_text) }
    private val functionalDataPerformanceText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_data_performance_value_text) }

    private val functionalDelayDataCollectDelaySelectedText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_delay_data_collect_value_text) }
    private val functionalDelayDataCollectBar by lazy { requireView().findViewById<AppCompatSeekBar>(R.id.functional_delay_data_collect_bar)}

    private val startUpdateSensorDataButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.start_update_sensor_data_button) }
    private val stopUpdateSensorDataButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.stop_update_sensor_data_button) }
    private val clearSensorDataButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.clear_sensor_data_button) }

    private var brightnessDataFlow: Job? = null
    private var orientationDataFlow: Job? = null
    private var accelerationDataFlow: Job? = null

    private var reactiveBrightnessValue = SensorResult(sensorType = TYPE_LIGHT)
    private var reactiveBrightnessMaxValue = SensorResult(sensorType = TYPE_LIGHT)
    private var reactiveBrightnessMinValue = SensorResult(sensorType = TYPE_LIGHT)
    private var reactiveOrientationValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var reactiveOrientationMaxValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var reactiveOrientationMinValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var reactiveAccelerationValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var reactiveAccelerationMaxValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var reactiveAccelerationMinValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)

    private var brightnessSensorDataGenerated = 0L
    private var orientationSensorDataGenerated = 0L
    private var accelerationSensorDataGenerated = 0L

    private var reactiveBrightnessSensorDataCollected = 0L
    private var reactiveOrientationSensorDataCollected = 0L
    private var reactiveAccelerationSensorDataCollected = 0L

    private var functionalBrightnessSensorDataCollected = 0L
    private var functionalOrientationSensorDataCollected = 0L
    private var functionalAccelerationSensorDataCollected = 0L

    private var functionalBrightnessValue = SensorResult(sensorType = TYPE_LIGHT)
    private var functionalBrightnessMaxValue = SensorResult(sensorType = TYPE_LIGHT)
    private var functionalBrightnessMinValue = SensorResult(sensorType = TYPE_LIGHT)
    private var functionalOrientationValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var functionalOrientationMaxValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var functionalOrientationMinValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var functionalAccelerationValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var functionalAccelerationMaxValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var functionalAccelerationMinValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)

    private var shouldCollectSensorData = false
    private var delayToCollectData = 500
    private val minDelayToCollectData = 1
    private val defaultDelayToCollectData = 500

    override var uiConfigurationViewState = UiConfigurationViewState(
        showToolbar = true,
        statusBarColor = R.color.colorPrimaryDark.some(),
        toolbarColor = R.color.colorPrimary.some(),
        toolbarNavigationIconColor = R.color.white.some()
    )

    override fun handleUiConfigurationViewState(uiConfigurationViewState: UiConfigurationViewState) = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        clearSensorData()
    }

    private fun setupListeners() {
        startUpdateSensorDataButton.setOnClickListener { startUpdateSensorData() }
        stopUpdateSensorDataButton.setOnClickListener { stopUpdateSensorData() }
        clearSensorDataButton.setOnClickListener { clearSensorData() }

        functionalDelayDataCollectBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (progress < minDelayToCollectData) {
                    seekBar.progress = minDelayToCollectData
                }
                delayToCollectData = progress
                functionalDelayDataCollectDelaySelectedText.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { /* no-op */ }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { /* no-op */ }
        })
    }

    private fun startUpdateSensorData() {
        shouldCollectSensorData = true
        startUpdateSensorDataButton.isEnabled = false

        brightnessDataFlow = lifecycleScope.launchWhenStarted {
            sensorsViewModel.getBrightSensorFlow().let { flow ->
                flow.cancellable().collectLatest { sensorData ->
                    brightnessSensorDataGenerated = sensorData.numberOfDataGenerated
                    reactiveBrightnessSensorDataCollected++
                    reactiveBrightnessValue = sensorData

                    updateDataCollected()

                    reactiveBrightnessResultText.text = reactiveBrightnessValue.formatToString(requireContext())

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
                    orientationSensorDataGenerated = sensorData.numberOfDataGenerated
                    reactiveOrientationSensorDataCollected++
                    reactiveOrientationValue = sensorData

                    updateDataCollected()

                    reactiveOrientationResultText.text = reactiveOrientationValue.formatToString(requireContext())

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
                    accelerationSensorDataGenerated = sensorData.numberOfDataGenerated
                    reactiveAccelerationSensorDataCollected++
                    reactiveAccelerationValue = sensorData

                    updateDataCollected()

                    reactiveAccelerationResultText.text = reactiveAccelerationValue.formatToString(requireContext())

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

        sensorsViewModel.startMeasureSensorData()

        startCollectFunctionalData()
    }

    private fun startCollectFunctionalData() {
        sensorsViewModel.viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.Default) {
                    while(shouldCollectSensorData) {
                        withContext(Dispatchers.Main) {
                            updateFunctionalSensorData()
                        }
                        Thread.sleep(delayToCollectData.toLong())
                    }
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

        maxTextToUpdate.text = maxValueToCompareWith.formatToString(requireContext())
        minTextToUpdate.text = minValueToCompareWith.formatToString(requireContext())
    }

    private fun stopUpdateSensorData() {
        shouldCollectSensorData = false
        brightnessDataFlow?.cancel()
        orientationDataFlow?.cancel()
        accelerationDataFlow?.cancel()
        startUpdateSensorDataButton.isEnabled = true
        sensorsViewModel.stopMeasureSensorData()
    }

    private fun updateFunctionalSensorData() {
        functionalBrightnessSensorDataCollected++
        functionalOrientationSensorDataCollected++
        functionalAccelerationSensorDataCollected++

        functionalBrightnessValue = sensorsViewModel.getBrightnessSensorData()
        functionalOrientationValue = sensorsViewModel.getOrientationSensorData()
        functionalAccelerationValue = sensorsViewModel.getAccelerometerSensorData()

        functionalBrightnessResultText.text = functionalBrightnessValue.formatToString(requireContext())
        functionalOrientationResultText.text = functionalOrientationValue.formatToString(requireContext())
        functionalAccelerationResultText.text = functionalAccelerationValue.formatToString(requireContext())

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

        updateDataCollected()
    }

    private fun updateDataCollected() {
        val totalReactiveCollectedValue = reactiveBrightnessSensorDataCollected + reactiveOrientationSensorDataCollected + reactiveAccelerationSensorDataCollected
        val totalFunctionalCollectedValue = functionalBrightnessSensorDataCollected + functionalOrientationSensorDataCollected + functionalAccelerationSensorDataCollected
        val totalGeneratedValue = brightnessSensorDataGenerated + orientationSensorDataGenerated + accelerationSensorDataGenerated

        reactiveDataCollectedText.text = getString(R.string.data_collected_value, totalReactiveCollectedValue)
        reactiveDataGeneratedText.text = getString(R.string.data_generated_value, totalGeneratedValue)
        reactiveDataPerformanceText.text = getString(R.string.data_performance_value, formatToPercentage(totalReactiveCollectedValue, totalGeneratedValue))

        functionalDataCollectedText.text = getString(R.string.data_collected_value, totalFunctionalCollectedValue)
        functionalDataGeneratedText.text = getString(R.string.data_generated_value, totalGeneratedValue)
        functionalDataPerformanceText.text = getString(R.string.data_performance_value, formatToPercentage(totalFunctionalCollectedValue, totalGeneratedValue))
    }

    private fun clearSensorData() {
        stopUpdateSensorData()

        sensorsViewModel.resetMeasureSensorData()

        brightnessSensorDataGenerated = 0L
        orientationSensorDataGenerated = 0L
        accelerationSensorDataGenerated = 0L

        reactiveBrightnessSensorDataCollected = 0L
        reactiveOrientationSensorDataCollected = 0L
        reactiveAccelerationSensorDataCollected = 0L

        functionalBrightnessSensorDataCollected = 0L
        functionalOrientationSensorDataCollected = 0L
        functionalAccelerationSensorDataCollected = 0L

        reactiveBrightnessValue.reset()
        reactiveBrightnessMaxValue.reset()
        reactiveBrightnessMinValue.reset()

        reactiveOrientationValue.reset()
        reactiveOrientationMaxValue.reset()
        reactiveOrientationMinValue.reset()

        reactiveAccelerationValue.reset()
        reactiveAccelerationMaxValue.reset()
        reactiveAccelerationMinValue.reset()

        functionalBrightnessValue.reset()
        functionalBrightnessMaxValue.reset()
        functionalBrightnessMinValue.reset()

        functionalOrientationValue.reset()
        functionalOrientationMaxValue.reset()
        functionalOrientationMinValue.reset()

        functionalAccelerationValue.reset()
        functionalAccelerationMaxValue.reset()
        functionalAccelerationMinValue.reset()

        reactiveBrightnessResultText.text = reactiveBrightnessValue.formatToString(requireContext())
        reactiveBrightnessMaxResultText.text = reactiveBrightnessMaxValue.formatToString(requireContext())
        reactiveBrightnessMinResultText.text = reactiveBrightnessMinValue.formatToString(requireContext())

        reactiveOrientationResultText.text = reactiveOrientationValue.formatToString(requireContext())
        reactiveOrientationMaxResultText.text = reactiveOrientationMaxValue.formatToString(requireContext())
        reactiveOrientationMinResultText.text = reactiveOrientationMinValue.formatToString(requireContext())

        reactiveAccelerationResultText.text = reactiveAccelerationValue.formatToString(requireContext())
        reactiveAccelerationMaxResultText.text = reactiveAccelerationMaxValue.formatToString(requireContext())
        reactiveAccelerationMinResultText.text = reactiveAccelerationMinValue.formatToString(requireContext())

        functionalBrightnessResultText.text = functionalBrightnessValue.formatToString(requireContext())
        functionalBrightnessMaxResultText.text = functionalBrightnessMaxValue.formatToString(requireContext())
        functionalBrightnessMinResultText.text = functionalBrightnessMinValue.formatToString(requireContext())

        functionalOrientationResultText.text = functionalOrientationValue.formatToString(requireContext())
        functionalOrientationMaxResultText.text = functionalOrientationMaxValue.formatToString(requireContext())
        functionalOrientationMinResultText.text = functionalOrientationMinValue.formatToString(requireContext())

        functionalAccelerationResultText.text = functionalAccelerationValue.formatToString(requireContext())
        functionalAccelerationMaxResultText.text = functionalAccelerationMaxValue.formatToString(requireContext())
        functionalAccelerationMinResultText.text = functionalAccelerationMinValue.formatToString(requireContext())

        delayToCollectData = defaultDelayToCollectData
        functionalDelayDataCollectBar.progress = defaultDelayToCollectData

        updateDataCollected()
    }

    private fun formatToPercentage(a: Long, b: Long) : String {
        return if (b != 0L) {
            "${
                String.format("%.2f", (a.toFloat() / b.toFloat()) * 100.0)
            }%"
        } else {
            "0,00%"
        }

    }
}