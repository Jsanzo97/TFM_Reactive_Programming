package com.example.data.datastore

import arrow.core.Option
import com.example.data.entity.DataTeam
import com.example.data.error.LocalDataError
import kotlinx.coroutines.flow.Flow

/**
 * Definition of the functions in the Data layer that we need to implement in the next layer
 */
interface LocalTeamsDatastore {

    /**
     * Saves teams in the database
     * @param dataTeams teams to be stored in the database
     * @return None if all was correct, LocalDataError if not
     */
    suspend fun saveTeam(dataTeams: List<DataTeam>): Option<LocalDataError>

    /**
     * Saves team in the database
     * @param dataTeam to be stored in the database
     * @return None if all was correct, LocalDataError if not
     */
    suspend fun saveTeam(dataTeam: DataTeam): Option<LocalDataError>

    /**
     * Remove all data from database
     * @return None if all was correct, LocalDataError if not
     */
    suspend fun clearDatabase(): Option<LocalDataError>

    /**
     * Returns data flow with the teams of the database
     * @return flow with teams data
     */
    suspend fun getTeamsReactive(): Flow<List<DataTeam>>

    /**
     * Returns the teams from database
     * @return list with the teams
     */
    fun getTeamsFunctional(): List<DataTeam>

    /**
     * Update team from databse
     * @param dataTeam team to be updated
     */
    suspend fun updateTeam(dataTeam: DataTeam): Option<LocalDataError>

    /**
     * Remove team from database
     * @param dataTeam team to be removed
     */
    suspend fun removeTeam(dataTeam: DataTeam): Option<LocalDataError>

}