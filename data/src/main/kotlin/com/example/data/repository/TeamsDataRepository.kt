package com.example.data.repository

import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.example.data.datastore.LocalTeamsDatastore
import com.example.data.entity.*
import com.example.data.error.toCustomError
import com.example.domain.entity.*
import com.example.domain.error.CustomError
import com.example.domain.error.UnknownIOError
import com.example.domain.repository.TeamsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class TeamsDataRepository (
    private val localTeamsDatastore: LocalTeamsDatastore,
    private val dispatcher: CoroutineDispatcher
) : TeamsRepository {

    override suspend fun saveTeam(team: List<Team>): Option<CustomError> = withContext(dispatcher) {
        localTeamsDatastore.saveTeam(team.map { it.toDataTeam() }).fold(
            {
                None
            },
            {
                it.toCustomError().some()
            }
        )
    }

    override suspend fun saveTeam(team: Team): Option<CustomError> = withContext(dispatcher) {
        localTeamsDatastore.saveTeam(team.toDataTeam()).fold(
            {
                None
            },
            {
                it.toCustomError().some()
            }
        )
    }

    override suspend fun clearDatabase(): Option<CustomError> = withContext(dispatcher) {
        localTeamsDatastore.clearDatabase().fold({
                None
            },
            {
                it.toCustomError().some()
            })
    }

    override fun getTeamsReactive(): Flow<List<Team>> =
        localTeamsDatastore.getTeamsReactive().map {
            it.map { dataTeam -> dataTeam.toTeam() }
        }


    override fun getTeamsFunctional(): List<Team> =
        localTeamsDatastore.getTeamsFunctional().map {
            it.toTeam()
        }


    override suspend fun updateTeam(team: Team): Option<CustomError> = withContext(dispatcher) {
        localTeamsDatastore.updateTeam(team.toDataTeam()).fold(
            {
                None
            },
            {
                it.toCustomError().some()
            }
        )
    }

    override suspend fun removeTeam(team: Team): Option<CustomError> = withContext(dispatcher) {
        localTeamsDatastore.removeTeam(team.toDataTeam()).fold(
            {
                None
            },
            {
                it.toCustomError().some()
            }
        )
    }

}