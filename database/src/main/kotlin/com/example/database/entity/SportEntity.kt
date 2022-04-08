package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.data.entity.DataSport

@Entity(
    tableName = "Sport",
    primaryKeys = ["name", "team_ID"],
    foreignKeys = [ForeignKey(
        entity = TeamEntity::class,
        parentColumns = ["team_id"],
        childColumns = ["team_ID"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SportEntity (
    val name: String,

    @ColumnInfo(name = "num_of_players")
    val numOfPlayers: Int,

    @ColumnInfo(name = "team_ID", index = true)
    val teamId: Long
)

fun DataSport.toSportEntity(teamId: Long) = SportEntity(
    name, numOfPlayers, teamId
)

fun SportEntity.toDataSport() = DataSport(
    name, numOfPlayers
)