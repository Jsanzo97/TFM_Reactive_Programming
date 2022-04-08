package com.example.data.datastore

import com.example.data.entity.DataTeam
import com.example.data.error.LocalDataError
import arrow.core.Option
import com.example.domain.entity.Team
import kotlinx.coroutines.flow.Flow

interface LocalTeamsDatastore {

    suspend fun saveTeam(dataTeam: List<DataTeam>): Option<LocalDataError>
    suspend fun saveTeam(dataTeam: DataTeam): Option<LocalDataError>
    suspend fun clearDatabase(): Option<LocalDataError>

    fun getTeamsReactive(): Flow<List<DataTeam>>
    fun getTeamsFunctional(): List<DataTeam>

    suspend fun updateTeam(dataTeam: DataTeam): Option<LocalDataError>
    suspend fun removeTeam(dataTeam: DataTeam): Option<LocalDataError>

}