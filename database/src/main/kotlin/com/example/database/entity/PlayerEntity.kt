package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.data.entity.DataPlayer

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

fun DataPlayer.toPlayerEntity(teamId: Long) = PlayerEntity(
    name, age, teamId
)

fun PlayerEntity.toDataPlayer() = DataPlayer(
    name, age
)