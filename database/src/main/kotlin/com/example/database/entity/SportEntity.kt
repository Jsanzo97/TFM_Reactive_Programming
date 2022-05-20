package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.data.entity.DataSport

/**
 * Represent sport in the database
 * @param name name of the sport
 * @param numOfPlayers num of players that plays the sport
 * @param teamId id of the team that plays this sport
 */
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

/**
 * Convert DataSport to SportEntity
 * @return new SportEntity with DataSport values
 */
fun DataSport.toSportEntity(teamId: Long) = SportEntity(
    name, numOfPlayers, teamId
)

/**
 * Convert SportEntity to DataSport
 * @return new DataSport with SportEntity values
 */
fun SportEntity.toDataSport() = DataSport(
    name, numOfPlayers
)