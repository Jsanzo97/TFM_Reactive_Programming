package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entity.PlayerEntity
import com.example.database.entity.SportEntity
import com.example.database.entity.TeamEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data access object to perform the database operations
 */
@Dao
abstract class TeamsDao {

    /**
     * Saves sport in the database
     * @param sportEntity sport that will be stored
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveSport(sportEntity: SportEntity)

    /**
     * Saves player in the database
     * @param playerEntity player that will be stored
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun savePlayer(playerEntity: PlayerEntity)

    /**
     * Saves team in the database
     * @param teamEntity team that will be stored
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveTeam(teamEntity: TeamEntity)

    /**
     * Removes all sports in the database
     */
    @Query("delete from Sport")
    abstract suspend fun deleteSports()

    /**
     * Removes all players in the database
     */
    @Query("delete from Player")
    abstract suspend fun deletePlayers()

    /**
     * Removes all teams from database
     */
    @Query("delete from Team")
    abstract suspend fun deleteTeams()

    /**
     * Obtains all teams from database
     * @return data flow with the teams list
     */
    @Query("select * from Team")
    abstract fun getTeamsReactive(): Flow<List<TeamEntity>>

    /**
     * Obtains sport of the team selected
     * @param teamId id of the team to get the sport
     * @return data flow with the sport
     */
    @Query("select * from Sport s where s.`team_ID` == :teamId")
    abstract fun getSportReactive(teamId: Long): Flow<SportEntity>

    /**
     * Obtains all players of the team selected
     * @param teamId id of the team to get the players
     * @return data flow with the players list
     */
    @Query("select * from Player p where p.`team_ID` == :teamId")
    abstract fun getPlayersReactive(teamId: Long): Flow<List<PlayerEntity>>

    /**
     * Obtains all teams from database
     * @return teams list
     */
    @Query("select * from Team")
    abstract fun getTeamsFunctional(): List<TeamEntity>

    /**
     * Obtains sport of the team selected
     * @param teamId id of the team to get the sport
     * @return sport of the team selected
     */
    @Query("select * from Sport s where s.`team_ID` == :teamId")
    abstract fun getSportFunctional(teamId: Long): SportEntity

    /**
     * Obtains all players of the team selected
     * @param teamId id of the team to get the players
     * @return players of the team selected
     */
    @Query("select * from Player p where p.`team_ID` == :teamId")
    abstract fun getPlayersFunctional(teamId: Long): List<PlayerEntity>

    /**
     * Updates team of the database
     * @param teamId id of the team that will be updated
     * @param newTeamName new name for the team
     */
    @Query("update Team set name = :newTeamName where team_id = :teamId")
    abstract suspend fun updateTeam(teamId: Long, newTeamName: String)

    /**
     * Removes team from the database
     * @param teamId id of the team that will be removed
     */
    @Query("delete from Team where team_id = :teamId")
    abstract fun removeTeam(teamId: Long)

}