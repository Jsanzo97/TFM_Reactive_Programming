package com.example.reactiveprogramming.ui.dataManagement

import android.os.AsyncTask
import androidx.lifecycle.*
import com.example.domain.entity.*
import com.example.domain.usecase.*
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
                        _dataManagementViewModelStateFlow.value = ErrorInOperation("Error al actualizar el equipo $team")
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
                        _dataManagementViewModelStateFlow.value = ErrorInOperation("Error al borrar el equipo $team")
                    }
                )
            }
        }
    }

    fun createNewTeam() {
        _dataManagementViewModelStateFlow.value = CreatingTeam

        viewModelScope.launch {
            createTeamUseCase(
                generateRandomTeam()
            ).fold(
                {
                    _dataManagementViewModelStateFlow.value = CreatedTeam
                },
                {
                    _dataManagementViewModelStateFlow.value = ErrorInOperation("Error al crear el equipo")
                }
            )
        }
    }
}