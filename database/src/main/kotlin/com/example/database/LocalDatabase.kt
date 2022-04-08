package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.TeamsDao
import com.example.database.entity.PlayerEntity
import com.example.database.entity.SportEntity
import com.example.database.entity.TeamEntity

@Database(
    entities = [
        PlayerEntity::class,
        SportEntity::class,
        TeamEntity::class
    ],
    version = 1
)

abstract class LocalDatabase : RoomDatabase() {
    abstract fun teamsDao() : TeamsDao
}

