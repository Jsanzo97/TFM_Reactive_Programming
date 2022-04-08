package com.example.reactiveprogramming.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.*
import com.example.domain.usecase.InitializeDatabaseUseCase
import kotlinx.coroutines.launch

class HomeViewModel (
    private val initializeDatabaseUseCase: InitializeDatabaseUseCase
) : ViewModel() {

    private val splashScope = viewModelScope

    private val initializerDatabaseLiveData: MutableLiveData<HomeViewState> = MutableLiveData()

    fun  initializeDataBase(numberOfTeamsToCreate: Int) {
        initializerDatabaseLiveData.postValue(InitializingDatabase)

        val teamList = (0 until numberOfTeamsToCreate).map { generateRandomTeam(it) }

        splashScope.launch {
            initializeDatabaseUseCase(teamList).fold(
                {
                    initializerDatabaseLiveData.postValue(InitializedDatabase)
                },
                {
                    initializerDatabaseLiveData.postValue(ErrorInOperation(it.toString()))
                }
            )
        }
    }

    fun getInitializerDatabaseLiveData() = initializerDatabaseLiveData

}