package com.example.reactiveprogramming.ui.sensors

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.reactiveprogramming.R
import com.example.sensors.formatToString
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class SensorsFragment: CustomFragment(R.layout.sensors_fragment) {

    private val sensorsViewModel: SensorsViewModel by viewModel()

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

        lifecycleScope.launchWhenStarted {
            sensorsViewModel.brightSensorFlow.let { flow ->
                flow.collectLatest { sensorData ->
                    view.findViewById<MaterialTextView>(R.id.brightness_reactive_value_text).text =
                        sensorData.formatToString()
                }
            }
        }
    }
}