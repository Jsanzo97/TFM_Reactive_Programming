package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.data.entity.DataPlayer
import com.example.data.entity.DataSport
import com.example.data.entity.DataTeam

@Entity(
    tableName = "Team",
    primaryKeys = ["team_id"]
)
data class TeamEntity (
    @ColumnInfo(name = "team_id")
    val teamId: Long,
    val name: String
)

fun DataTeam.toTeamEntity() = TeamEntity(
    teamId, name
)

fun TeamEntity.toDataTeam(sport: DataSport, players: List<DataPlayer>) = DataTeam(
    teamId, name, sport, players
)
