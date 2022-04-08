package com.example.domain.repository

import arrow.core.Option
import com.example.domain.entity.RepositoryResult
import com.example.domain.entity.Sport
import com.example.domain.entity.Team
import com.example.domain.error.CustomError
import kotlinx.coroutines.flow.Flow

interface TeamsRepository {

    suspend fun saveTeam(team: List<Team>): Option<CustomError>
    suspend fun saveTeam(team: Team): Option<CustomError>
    suspend fun clearDatabase(): Option<CustomError>

    fun getTeamsReactive(): Flow<List<Team>>
    fun getTeamsFunctional(): List<Team>

    suspend fun updateTeam(team: Team): Option<CustomError>
    suspend fun removeTeam(team: Team): Option<CustomError>

}