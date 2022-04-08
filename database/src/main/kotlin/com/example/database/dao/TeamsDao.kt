package com.example.database.dao

import androidx.room.*
import com.example.database.entity.PlayerEntity
import com.example.database.entity.SportEntity
import com.example.database.entity.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TeamsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveSport(sportEntity: SportEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun savePlayer(playerEntity: PlayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveTeam(teamEntity: TeamEntity)

    @Query("delete from Sport")
    abstract suspend fun deleteSports()

    @Query("delete from Player")
    abstract suspend fun deletePlayers()

    @Query("delete from Team")
    abstract suspend fun deleteTeams()

    @Query("select * from Team")
    abstract fun getTeamsReactive(): Flow<List<TeamEntity>>

    @Query("select * from Sport s where s.`team_ID` == :teamId")
    abstract fun getSportReactive(teamId: Long): Flow<SportEntity>

    @Query("select * from Player p where p.`team_ID` == :teamId")
    abstract fun getPlayersReactive(teamId: Long): Flow<List<PlayerEntity>>

    @Query("select * from Team")
    abstract fun getTeamsFunctional(): List<TeamEntity>

    @Query("select * from Sport s where s.`team_ID` == :teamId")
    abstract fun getSportFunctional(teamId: Long): SportEntity

    @Query("select * from Player p where p.`team_ID` == :teamId")
    abstract fun getPlayersFunctional(teamId: Long): List<PlayerEntity>

    @Query("update Team set name = :newTeamName where team_id = :teamId")
    abstract suspend fun updateTeam(teamId: Long, newTeamName: String)

    @Query("delete from Team where team_id = :teamId")
    abstract fun removeTeam(teamId: Long)


}