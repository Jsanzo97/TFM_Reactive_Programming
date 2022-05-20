package com.example.domain.repository

import arrow.core.Option
import com.example.domain.entity.Team
import com.example.domain.error.CustomError
import kotlinx.coroutines.flow.Flow

/**
 * Definition of the functions in the Domain layer that we need to implement in the next layer
 */
interface TeamsRepository {

    /**
     * Saves teams in the database
     * @param dataTeams teams to be stored in the database
     * @return None if all was correct, LocalDataError if not
     */
    suspend fun saveTeam(dataTeams: List<Team>): Option<CustomError>

    /**
     * Saves team in the database
     * @param dataTeam to be stored in the database
     * @return None if all was correct, LocalDataError if not
     */
    suspend fun saveTeam(dataTeam: Team): Option<CustomError>

    /**
     * Remove all data from database
     * @return None if all was correct, LocalDataError if not
     */
    suspend fun clearDatabase(): Option<CustomError>

    /**
     * Returns data flow with the teams of the database
     * @return flow with teams data
     */
    fun getTeamsReactive(): Flow<List<Team>>

    /**
     * Returns the teams from database
     * @return list with the teams
     */
    fun getTeamsFunctional(): List<Team>

    /**
     * Update team from databse
     * @param dataTeam team to be updated
     */
    suspend fun updateTeam(dataTeam: Team): Option<CustomError>

    /**
     * Remove team from database
     * @param dataTeam team to be removed
     */
    suspend fun removeTeam(dataTeam: Team): Option<CustomError>

}