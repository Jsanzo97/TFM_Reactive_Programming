package com.example.common

import android.view.MotionEvent
import android.view.WindowInsets
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.None
import arrow.core.Option
import arrow.core.or
import arrow.core.some

data class UiConfigurationViewState(
    var showToolbar: Boolean = false,
    var statusBarColor: Option<Int> = None,
    var toolbarColor: Option<Int> = None,
    var toolbarNavigationIconColor: Option<Int> = None,
    var insets: Option<WindowInsets> = None,
    var showLogoutButton: Boolean = false
)

class UiConfigurationViewModel : ViewModel() {

    private val uiConfigurationLiveData: MutableLiveData<UiConfigurationViewState> = MutableLiveData()

    private var currentState = UiConfigurationViewState()

    fun updateViewState(viewState: UiConfigurationViewState = currentState) {
        currentState = viewState.copy(insets = viewState.insets.or(currentState.insets))
        uiConfigurationLiveData.postValue(currentState)
    }

    fun setInsets(insets: WindowInsets) {
        currentState = currentState.copy(insets = insets.some())
        uiConfigurationLiveData.postValue(currentState)
    }

    val toolbarMotion: MutableLiveData<MotionEvent> = MutableLiveData()

    fun getUiConfigurationLiveData() = uiConfigurationLiveData

    fun getCurrentState() = currentState

}
