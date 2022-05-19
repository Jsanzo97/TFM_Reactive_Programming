package com.example.reactiveprogramming.ui.dataManagement

import com.example.domain.entity.Team

/**
 * View states for DataManagementFragment
 */
sealed class DataManagementViewState

// Initial states
object InitialState: DataManagementViewState()
object InitializingDatabase: DataManagementViewState()
object InitializedDatabase: DataManagementViewState()

// CRUD Operations states
object RetrievingReactiveTeams: DataManagementViewState()
object RetrievingFunctionalTeams: DataManagementViewState()
class RetrievedReactiveTeams(val teams: List<Team>): DataManagementViewState()
class RetrievedFunctionalTeams(val teams: List<Team>): DataManagementViewState()
object UpdatingTeam: DataManagementViewState()
object UpdatedTeam: DataManagementViewState()
object RemovingTeam: DataManagementViewState()
object RemovedTeam: DataManagementViewState()
object CreatingTeam: DataManagementViewState()
object CreatedTeam: DataManagementViewState()

// Error states
class ErrorInOperationWithId(val messageId: Int, val team: Team): DataManagementViewState()
class ErrorInOperation(val message: String): DataManagementViewState()
