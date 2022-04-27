package com.example.reactiveprogramming.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.generateRandomTeam
import com.example.domain.usecase.InitializeDatabaseUseCase
import com.example.reactiveprogramming.ui.dataManagement.DataManagementViewState
import com.example.reactiveprogramming.ui.dataManagement.InitialState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (
    private val initializeDatabaseUseCase: InitializeDatabaseUseCase
) : ViewModel() {

    private val splashScope = viewModelScope

    private val _initializerDatabaseLiveData = MutableLiveData<HomeViewState>()
    val initializerDatabaseLiveData: LiveData<HomeViewState> get() = _initializerDatabaseLiveData

    fun  initializeDataBase(numberOfTeamsToCreate: Int) {
        _initializerDatabaseLiveData.postValue(InitializingDatabase)

        val teamList = (0 until numberOfTeamsToCreate).map { generateRandomTeam(it) }

        splashScope.launch {
            initializeDatabaseUseCase(teamList).fold(
                {
                    _initializerDatabaseLiveData.postValue(InitializedDatabase)
                },
                {
                    _initializerDatabaseLiveData.postValue(ErrorInOperation(it.toString()))
                }
            )
        }
    }

}