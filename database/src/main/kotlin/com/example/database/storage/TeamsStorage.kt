package com.example.database.storage

import arrow.core.None
import com.example.data.datastore.LocalTeamsDatastore
import com.example.data.error.LocalDataError
import com.example.data.error.WritingError
import com.example.database.dao.TeamsDao
import arrow.core.Option
import arrow.core.some
import com.example.data.entity.*
import com.example.database.entity.*
import com.example.domain.entity.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.Exception

class TeamsStorage (
    private val teamsDao: TeamsDao
) : LocalTeamsDatastore {

    override suspend fun saveTeam(dataTeam: List<DataTeam>) : Option<LocalDataError> {
        return try {
            dataTeam.forEach { saveTeam(it) }
            None
        } catch (_: Exception) {
            WritingError.some()
        }
    }

    override suspend fun saveTeam(dataTeam: DataTeam): Option<LocalDataError> {
        return try {
            teamsDao.saveTeam(dataTeam.toTeamEntity())
            teamsDao.saveSport(dataTeam.sport.toSportEntity(dataTeam.teamId))
            dataTeam.players.forEach { dataPlayer ->
                teamsDao.savePlayer(dataPlayer.toPlayerEntity(dataTeam.teamId))
            }
            None
        } catch (_: Exception) {
            WritingError.some()
        }
    }


    override suspend fun clearDatabase(): Option<LocalDataError> {
        return try {
            teamsDao.deletePlayers()
            teamsDao.deleteSports()
            teamsDao.deleteTeams()

            None
        } catch (_: Exception) {
            WritingError.some()
        }
    }

    override fun getTeamsReactive(): Flow<List<DataTeam>> {
        return teamsDao.getTeamsReactive().map { teamEntityList ->
            teamEntityList.map { teamEntity ->
                teamEntity.toDataTeam(
                    teamsDao.getSportReactive(teamEntity.teamId).first().toDataSport(),
                    teamsDao.getPlayersReactive(teamEntity.teamId).first().map { it.toDataPlayer() }
                )
            }
        }
    }

    override fun getTeamsFunctional(): List<DataTeam> {
        return teamsDao.getTeamsFunctional().map { teamEntity ->
                teamEntity.toDataTeam(
                    teamsDao.getSportFunctional(teamEntity.teamId).toDataSport(),
                    teamsDao.getPlayersFunctional(teamEntity.teamId).map { it.toDataPlayer() }
                )
        }
    }

    override suspend fun updateTeam(dataTeam: DataTeam): Option<LocalDataError> {
        return try {
            val newTeamName = "${dataTeam.name} Updated"
            teamsDao.updateTeam(dataTeam.teamId, newTeamName)
            None
        } catch (_: Exception) {
            WritingError.some()
        }

    }

    override suspend fun removeTeam(dataTeam: DataTeam): Option<LocalDataError> {
        return try {
            teamsDao.removeTeam(dataTeam.teamId)
            None
        } catch (_: Exception) {
            WritingError.some()
        }
    }

}