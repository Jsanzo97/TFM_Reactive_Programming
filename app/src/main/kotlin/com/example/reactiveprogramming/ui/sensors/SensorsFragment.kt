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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Represents the difference of retrieve data from sensor by reactive way or by functional way
 */
class SensorsFragment: CustomFragment(R.layout.sensors_fragment) {

    // ViewModel to manage view actions
    private val sensorsViewModel: SensorsViewModel by viewModel()

    // Bind reactive view elements
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

    // Bind functional view elements
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

    // Bind control view elements
    private val startUpdateSensorDataButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.start_update_sensor_data_button) }
    private val stopUpdateSensorDataButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.stop_update_sensor_data_button) }
    private val clearSensorDataButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.clear_sensor_data_button) }

    // References to flows that maybe we need to pause or cancel
    private var brightnessDataFlow: Job? = null
    private var orientationDataFlow: Job? = null
    private var accelerationDataFlow: Job? = null

    // Values of data retrieved from sensor by reactive way
    private var reactiveBrightnessValue = SensorResult(sensorType = TYPE_LIGHT)
    private var reactiveBrightnessMaxValue = SensorResult(sensorType = TYPE_LIGHT)
    private var reactiveBrightnessMinValue = SensorResult(sensorType = TYPE_LIGHT)
    private var reactiveOrientationValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var reactiveOrientationMaxValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var reactiveOrientationMinValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var reactiveAccelerationValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var reactiveAccelerationMaxValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var reactiveAccelerationMinValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)

    // Total data generated by sensors
    private var brightnessSensorDataGenerated = 0L
    private var orientationSensorDataGenerated = 0L
    private var accelerationSensorDataGenerated = 0L

    // Total data collected from sensors by reactive way
    private var reactiveBrightnessSensorDataCollected = 0L
    private var reactiveOrientationSensorDataCollected = 0L
    private var reactiveAccelerationSensorDataCollected = 0L

    // Total data collected from sensors by functional way
    private var functionalBrightnessSensorDataCollected = 0L
    private var functionalOrientationSensorDataCollected = 0L
    private var functionalAccelerationSensorDataCollected = 0L

    // Values of data retrieved from sensor by functional way
    private var functionalBrightnessValue = SensorResult(sensorType = TYPE_LIGHT)
    private var functionalBrightnessMaxValue = SensorResult(sensorType = TYPE_LIGHT)
    private var functionalBrightnessMinValue = SensorResult(sensorType = TYPE_LIGHT)
    private var functionalOrientationValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var functionalOrientationMaxValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var functionalOrientationMinValue = SensorResult(sensorType = TYPE_ROTATION_VECTOR)
    private var functionalAccelerationValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var functionalAccelerationMaxValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)
    private var functionalAccelerationMinValue = SensorResult(sensorType = TYPE_LINEAR_ACCELERATION)

    // Control the data collect
    private var shouldCollectSensorData = false
    private var delayToCollectData = 500
    private val minDelayToCollectData = 1
    private val defaultDelayToCollectData = 500

    // UI Configuration for custom toolbar
    override var uiConfigurationViewState = UiConfigurationViewState(
        statusBarColor = R.color.colorPrimaryDark.some(),
        toolbarColor = R.color.colorPrimary.some(),
        toolbarNavigationIconColor = R.color.white.some()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        clearSensorData()
    }

    /**
     * Set view actions
     */
    private fun setupListeners() {
        startUpdateSensorDataButton.setOnClickListener {
            startUpdateSensorData()
            startCollectReactiveData()
            startCollectFunctionalData()
        }
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

    /**
     * Starts the sensor data production
     */
    private fun startUpdateSensorData() {
        shouldCollectSensorData = true
        startUpdateSensorDataButton.isEnabled = false
        sensorsViewModel.startMeasureSensorData()
    }

    /**
     * Starts the flows to collect the sensors data by reactive way
     */
    private fun startCollectReactiveData() {
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
    }

    /**
     * Starts the collection of the sensors data by functional way
     */
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

    /**
     * Check if should update the min or max value for every sensor
     * @param sensorData data retrieved from sensor
     * @param maxValueToCompareWith latest max value retrieved
     * @param minValueToCompareWith latest min value retrieved
     * @param maxTextToUpdate text that should be updated if max value is updated
     * @param minTextToUpdate text that should be updated if min value is updated
     */
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

    /**
     * Stop the sensors data production
     */
    private fun stopUpdateSensorData() {
        shouldCollectSensorData = false
        brightnessDataFlow?.cancel()
        orientationDataFlow?.cancel()
        accelerationDataFlow?.cancel()
        startUpdateSensorDataButton.isEnabled = true
        sensorsViewModel.stopMeasureSensorData()
    }

    /**
     * Retrieve data from sensor by functional way and update corresponding text
     */
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

    /**
     * Update total data collected, generated and the efficiency of reactive way and functional way
     */
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

    /**
     * Remove all sensor data and reset all values and text
     */
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

    /**
     * Get percentage of two numbers, for example: 1 and 3 is 0,33
     * @param a number to divide
     * @param b number to divide with
     * @return percentage as String or 0,00 if the b parameter is 0
     */
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