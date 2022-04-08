package com.example.reactiveprogramming.ui.sensors

import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.reactiveprogramming.R

class SensorsFragment: CustomFragment(R.layout.sensors_fragment) {

    override var uiConfigurationViewState = UiConfigurationViewState(
        showToolbar = true,
        statusBarColor = R.color.colorPrimaryDark.some(),
        toolbarColor = R.color.colorPrimary.some(),
        toolbarNavigationIconColor = R.color.white.some(),
        showLogoutButton = true
    )

    override fun handleUiConfigurationViewState(uiConfigurationViewState: UiConfigurationViewState) = Unit

}