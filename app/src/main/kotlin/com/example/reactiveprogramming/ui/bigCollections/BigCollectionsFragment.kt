package com.example.reactiveprogramming.ui.bigCollections

import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.reactiveprogramming.R

class BigCollectionsFragment: CustomFragment(R.layout.big_collections_fragment) {

    override var uiConfigurationViewState = UiConfigurationViewState(
        showToolbar = true,
        statusBarColor = R.color.colorPrimaryDark.some(),
        toolbarColor = R.color.colorPrimary.some(),
        toolbarNavigationIconColor = R.color.white.some(),
        showLogoutButton = true
    )

    override fun handleUiConfigurationViewState(uiConfigurationViewState: UiConfigurationViewState) = Unit

}