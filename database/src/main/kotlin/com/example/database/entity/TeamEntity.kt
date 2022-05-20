package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.data.entity.DataPlayer
import com.example.data.entity.DataSport
import com.example.data.entity.DataTeam

/**
 * Represent team in the database
 * @param teamId id of the team
 * @param name name of the team
 */
@Entity(
    tableName = "Team",
    primaryKeys = ["team_id"]
)
data class TeamEntity (
    @ColumnInfo(name = "team_id")
    val teamId: Long,
    val name: String
)

/**
 * Convert DataTeam to TeamEntity
 * @return new TeamEntity with the DataTeam values
 */
fun DataTeam.toTeamEntity() = TeamEntity(
    teamId, name
)

/**
 * Convert TeamEntity to DataTeam
 * @return new DataTeam with the TeamEntity values
 */
fun TeamEntity.toDataTeam(sport: DataSport, players: List<DataPlayer>) = DataTeam(
    teamId, name, sport, players
)
