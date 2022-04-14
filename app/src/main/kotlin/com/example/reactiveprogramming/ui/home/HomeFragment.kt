package com.example.reactiveprogramming.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.reactiveprogramming.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : CustomFragment(R.layout.home_fragment), LifecycleObserver {

    private val viewModel: HomeViewModel by viewModel()

    private val numberOfTeamsToCreate = 5

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

        viewModel.getInitializerDatabaseLiveData().observe(this, Observer { state ->
            when (state) {
                is InitializingDatabase -> showProgressDialog()
                is InitializedDatabase -> hideProgressDialog()
                is ErrorInOperation -> showError(state.message)
            }
        })

        viewModel.initializeDataBase(numberOfTeamsToCreate)
    }
}