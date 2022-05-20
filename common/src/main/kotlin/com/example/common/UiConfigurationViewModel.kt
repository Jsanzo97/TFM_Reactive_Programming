package com.example.common

import android.view.WindowInsets
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.None
import arrow.core.Option
import arrow.core.or
import arrow.core.some

/**
 * Configuration state for the views
 * @param statusBarColor changes the color of the status bar
 * @param toolbarColor changes the color of the toolbar
 * @param toolbarNavigationIconColor changes the color of the toolbar icon
 * @param insets insets of the window to fit the sizes
 */
data class UiConfigurationViewState(
    var statusBarColor: Option<Int> = None,
    var toolbarColor: Option<Int> = None,
    var toolbarNavigationIconColor: Option<Int> = None,
    var insets: Option<WindowInsets> = None
)

/**
 * Manages the changes of the ui configuration
 */
class UiConfigurationViewModel : ViewModel() {

    private val uiConfigurationLiveData: MutableLiveData<UiConfigurationViewState> = MutableLiveData()

    private var currentState = UiConfigurationViewState()

    /**
     * Updates the ui configuration state
     * @param viewState new state for the view
     */
    fun updateViewState(viewState: UiConfigurationViewState = currentState) {
        currentState = viewState.copy(insets = viewState.insets.or(currentState.insets))
        uiConfigurationLiveData.postValue(currentState)
    }

    /**
     * Set the insets of the window
     * @param insets insets of the window
     */
    fun setInsets(insets: WindowInsets) {
        currentState = currentState.copy(insets = insets.some())
        uiConfigurationLiveData.postValue(currentState)
    }

    /**
     * Returns the ui configuration live data
     */
    fun getUiConfigurationLiveData() = uiConfigurationLiveData

    /**
     * Returns current state of ui configuration
     */
    fun getCurrentState() = currentState

}
