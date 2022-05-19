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

/**
 * ViewModel to manage the actions performed in the DataManagementFragment
 * @param initializeDatabaseUseCase Initialize the database with random data
 * @param getTeamsReactiveUseCase Retrieve the teams from database by reactive way
 * @param getTeamsFunctionalUseCase Retrieve the teams from database by functional way
 * @param updateTeamUseCase Update random team from database
 * @param removeTeamUseCase Remove random team from database
 * @param createTeamUseCase Add new random team to database
 */
class DataManagementViewModel(
    private val initializeDatabaseUseCase: InitializeDatabaseUseCase,
    private val getTeamsReactiveUseCase: GetTeamsReactiveUseCase,
    private val getTeamsFunctionalUseCase: GetTeamsFunctionalUseCase,
    private val updateTeamUseCase: UpdateTeamUseCase,
    private val removeTeamUseCase: RemoveTeamUseCase,
    private val createTeamUseCase: CreateTeamUseCase
): ViewModel() {

    private val _dataManagementViewModelStateFlow = MutableStateFlow<DataManagementViewState>(InitialState)
    val dataManagementViewModelSateFlow: StateFlow<DataManagementViewState> get() = _dataManagementViewModelStateFlow

    /**
     * Fill the database with random data
     * @param numberOfTeamsToCreate number of random teams that will be created
     */
    fun initializeDataBase(numberOfTeamsToCreate: Int) {
        _dataManagementViewModelStateFlow.value = InitializingDatabase

        val teamList = (0 until numberOfTeamsToCreate).map { generateRandomTeam(it) }

        viewModelScope.launch {
            initializeDatabaseUseCase(teamList).fold(
                {
                    _dataManagementViewModelStateFlow.value = InitializedDatabase
                },
                {
                    _dataManagementViewModelStateFlow.value = ErrorInOperation(it.toString())
                }
            )
        }
    }

    /**
     * Retrieve the teams from database by reactive way
     */
    fun getTeamsReactive() {
        _dataManagementViewModelStateFlow.value = RetrievingReactiveTeams

        viewModelScope.launch {
            getTeamsReactiveUseCase().collectLatest {
                _dataManagementViewModelStateFlow.value = RetrievedReactiveTeams(it)
            }
        }
    }

    /**
     * Retrieve the teams from database by functional way
     */
    fun getTeamsFunctional() {
        _dataManagementViewModelStateFlow.value = RetrievingFunctionalTeams

        AsyncTask.execute {
            _dataManagementViewModelStateFlow.value =
                RetrievedFunctionalTeams(getTeamsFunctionalUseCase())
        }
    }

    /**
     * Update team from databse
     * @param team team to update
     */
    fun updateTeam(team: Team?) {
        team?.let {
            _dataManagementViewModelStateFlow.value = UpdatingTeam

            viewModelScope.launch {
                updateTeamUseCase(it).fold(
                    {
                        _dataManagementViewModelStateFlow.value = UpdatedTeam
                    },
                    {
                        _dataManagementViewModelStateFlow.value = ErrorInOperationWithId(R.string.error_update_team, team)
                    }
                )
            }
        }
    }

    /**
     * Remove team from database
     * @param team team to remove
     */
    fun removeTeam(team: Team?) {
        team?.let {
            _dataManagementViewModelStateFlow.value = RemovingTeam

            viewModelScope.launch {
                removeTeamUseCase(it).fold(
                    {
                        _dataManagementViewModelStateFlow.value = RemovedTeam
                    },
                    {
                        _dataManagementViewModelStateFlow.value = ErrorInOperationWithId(R.string.error_remove_team, team)
                    }
                )
            }
        }
    }

    /**
     * Store new random team in the database
     */
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
                    _dataManagementViewModelStateFlow.value = ErrorInOperationWithId(R.string.error_create_team, randomTeam)
                }
            )
        }
    }
}