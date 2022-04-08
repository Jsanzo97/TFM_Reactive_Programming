package com.example.reactiveprogramming.ui.dataManagement

import android.os.AsyncTask
import androidx.lifecycle.*
import com.example.domain.entity.*
import com.example.domain.usecase.*
import kotlinx.coroutines.launch

class DataManagementViewModel(
    getTeamsReactiveUseCase: GetTeamsReactiveUseCase,
    private val getTeamsFunctionalUseCase: GetTeamsFunctionalUseCase,
    private val updateTeamUseCase: UpdateTeamUseCase,
    private val removeTeamUseCase: RemoveTeamUseCase,
    private val createTeamUseCase: CreateTeamUseCase
): ViewModel() {

    private val dataManagementViewModelLiveData: MutableLiveData<DataManagementViewState> = MutableLiveData()

    private var retrievedTeamsFromDatabaseLiveData: LiveData<List<Team>> = getTeamsReactiveUseCase().asLiveData()
    private var retrievedTeamsFromDatabaseObserver = Observer<List<Team>> { teamList ->
        dataManagementViewModelLiveData.postValue(
            RetrievedReactiveTeams(
                teamList
            )
        )
    }

    fun getTeamsReactive() {
        if (!retrievedTeamsFromDatabaseLiveData.hasObservers()) {
            dataManagementViewModelLiveData.postValue(RetrievingTeams)
            retrievedTeamsFromDatabaseLiveData.observeForever(retrievedTeamsFromDatabaseObserver)
        }
    }

    fun getTeamsFunctional() {
        dataManagementViewModelLiveData.postValue(RetrievingTeams)
        AsyncTask.execute {
            dataManagementViewModelLiveData.postValue(
                RetrievedFunctionalTeams(
                    getTeamsFunctionalUseCase()
                )
            )
        }
    }

    fun updateTeam(team: Team?) {
        team?.let {
            dataManagementViewModelLiveData.postValue(UpdatingTeam)
            viewModelScope.launch {
                updateTeamUseCase(it).fold(
                    {
                        dataManagementViewModelLiveData.postValue(UpdatedTeam)
                    },
                    {
                        dataManagementViewModelLiveData.postValue(
                            ErrorInOperation("Error al actualizar el equipo $team")
                        )
                    }
                )
            }
        }
    }

    fun removeTeam(team: Team?) {
        team?.let {
            dataManagementViewModelLiveData.postValue(RemovingTeam)
            viewModelScope.launch {
                removeTeamUseCase(it).fold(
                    {
                        dataManagementViewModelLiveData.postValue(RemovedTeam)
                    },
                    {
                        dataManagementViewModelLiveData.postValue(
                            ErrorInOperation("Error al borrar el equipo $team")
                        )
                    }
                )
            }
        }
    }

    fun createNewTeam() {
        dataManagementViewModelLiveData.postValue(CreatingTeam)

        viewModelScope.launch {
            createTeamUseCase(
                generateRandomTeam()
            ).fold(
                {
                    dataManagementViewModelLiveData.postValue(CreatedTeam)
                },
                {
                    dataManagementViewModelLiveData.postValue(
                        ErrorInOperation("Error al crear el equipo")
                    )
                }
            )
        }
    }

    fun getTeamsBySportLiveData() = dataManagementViewModelLiveData

    override fun onCleared() {
        super.onCleared()
        retrievedTeamsFromDatabaseLiveData.removeObserver(retrievedTeamsFromDatabaseObserver)
    }
}