package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.data.entity.DataPlayer

/**
 * Represent player in the database
 * @param name name of the player
 * @param age age of the player
 * @param teamId id of the team
 */
@Entity(
    tableName = "Player",
    primaryKeys = ["name", "team_ID"],
    foreignKeys = [ForeignKey(
        entity = TeamEntity::class,
        parentColumns = ["team_id"],
        childColumns = ["team_ID"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PlayerEntity (
    val name: String,
    val age: Int,

    @ColumnInfo(name = "team_ID", index = true)
    val teamId: Long
)

/**
 * Convert DataPlayer to PlayerEntity
 * @return new PlayerEntity with DataPlayer values
 */
fun DataPlayer.toPlayerEntity(teamId: Long) = PlayerEntity(
    name, age, teamId
)

/**
 * Convert PlayerEntity to DataPlayer
 * @return new DataPlayer with PlayerEntity values
 */
fun PlayerEntity.toDataPlayer() = DataPlayer(
    name, age
)