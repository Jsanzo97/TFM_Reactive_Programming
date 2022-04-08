package com.example.reactiveprogramming.di.data

import androidx.room.Room
import com.example.data.datastore.LocalTeamsDatastore
import com.example.data.repository.TeamsDataRepository
import com.example.database.LocalDatabase
import com.example.database.storage.TeamsStorage
import com.example.domain.repository.TeamsRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE_NAME = "localStorage.db"

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), LocalDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }


    /* DAO */

    single {
        val database = get<LocalDatabase>()
        database.teamsDao()
    }


    /* REPOSITORY */

    single<TeamsRepository> { TeamsDataRepository(get(), Dispatchers.IO) }


    /* DATASTORE */

    single<LocalTeamsDatastore> { TeamsStorage(get()) }

}