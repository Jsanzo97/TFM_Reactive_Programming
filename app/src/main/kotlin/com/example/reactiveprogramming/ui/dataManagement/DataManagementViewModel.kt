package com.example.reactiveprogramming.ui.dataManagement

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Team
import com.example.domain.entity.generateRandomTeam
import com.example.domain.usecase.*
import com.example.reactiveprogramming.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DataManagementViewModel(
    private val getTeamsReactiveUseCase: GetTeamsReactiveUseCase,
    private val getTeamsFunctionalUseCase: GetTeamsFunctionalUseCase,
    private val updateTeamUseCase: UpdateTeamUseCase,
    private val removeTeamUseCase: RemoveTeamUseCase,
    private val createTeamUseCase: CreateTeamUseCase
): ViewModel() {

    private val _dataManagementViewModelStateFlow = MutableStateFlow<DataManagementViewState>(InitialState)
    val dataManagementViewModelSateFlow: StateFlow<DataManagementViewState> get() = _dataManagementViewModelStateFlow

    fun getTeamsReactive() {
        _dataManagementViewModelStateFlow.value = RetrievingReactiveTeams

        viewModelScope.launch {
            getTeamsReactiveUseCase().collectLatest {
                _dataManagementViewModelStateFlow.value = RetrievedReactiveTeams(it)
            }
        }
    }


    fun getTeamsFunctional() {
        _dataManagementViewModelStateFlow.value = RetrievingFunctionalTeams

        AsyncTask.execute {
            _dataManagementViewModelStateFlow.value =
                RetrievedFunctionalTeams(getTeamsFunctionalUseCase())
        }
    }

    fun updateTeam(team: Team?) {
        team?.let {
            _dataManagementViewModelStateFlow.value = UpdatingTeam

            viewModelScope.launch {
                updateTeamUseCase(it).fold(
                    {
                        _dataManagementViewModelStateFlow.value = UpdatedTeam
                    },
                    {
                        _dataManagementViewModelStateFlow.value = ErrorInOperation(R.string.error_update_team, team)
                    }
                )
            }
        }
    }

    fun removeTeam(team: Team?) {
        team?.let {
            _dataManagementViewModelStateFlow.value = RemovingTeam

            viewModelScope.launch {
                removeTeamUseCase(it).fold(
                    {
                        _dataManagementViewModelStateFlow.value = RemovedTeam
                    },
                    {
                        _dataManagementViewModelStateFlow.value = ErrorInOperation(R.string.error_remove_team, team)
                    }
                )
            }
        }
    }

    fun createNewTeam() {
        _dataManagementViewModelStateFlow.value = CreatingTeam

        val randomTeam = generateRandomTeam()

        viewModelScope.launch {
            createTeamUseCase(
                randomTeam
            ).fold(
                {
                    _dataManagementViewModelStateFlow.value = CreatedTeam
                },
                {
                    _dataManagementViewModelStateFlow.value = ErrorInOperation(R.string.error_create_team, randomTeam)
                }
            )
        }
    }
}