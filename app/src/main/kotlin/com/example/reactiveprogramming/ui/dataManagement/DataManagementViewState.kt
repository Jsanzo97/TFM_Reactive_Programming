package com.example.reactiveprogramming.ui.dataManagement

import com.example.domain.entity.Team

sealed class DataManagementViewState

object RetrievingTeams: DataManagementViewState()
class RetrievedReactiveTeams(val teams: List<Team>): DataManagementViewState()
class RetrievedFunctionalTeams(val teams: List<Team>): DataManagementViewState()
object UpdatingTeam: DataManagementViewState()
object UpdatedTeam: DataManagementViewState()
object RemovingTeam: DataManagementViewState()
object RemovedTeam: DataManagementViewState()
object CreatingTeam: DataManagementViewState()
object CreatedTeam: DataManagementViewState()
class ErrorInOperation(val message: String): DataManagementViewState()
