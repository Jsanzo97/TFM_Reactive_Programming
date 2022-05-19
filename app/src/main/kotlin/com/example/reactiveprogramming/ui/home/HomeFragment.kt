package com.example.reactiveprogramming.ui.home


import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.reactiveprogramming.R

/**
 * First view to show to user, it only have one text as welcome
 */
class HomeFragment : CustomFragment(R.layout.home_fragment) {

    // UI Configuration for custom toolbar
    override var uiConfigurationViewState = UiConfigurationViewState(
        statusBarColor = R.color.colorPrimaryDark.some(),
        toolbarColor = R.color.colorPrimary.some(),
        toolbarNavigationIconColor = R.color.white.some()
    )

}