package com.example.reactiveprogramming.di.data

import androidx.room.Room
import com.example.collections.storage.CollectionsStorage
import com.example.data.datastore.CollectionsDatastore
import com.example.data.datastore.LocalTeamsDatastore
import com.example.data.datastore.SensorDatastore
import com.example.data.repository.CollectionsDataRepository
import com.example.data.repository.SensorDataRepository
import com.example.data.repository.TeamsDataRepository
import com.example.database.LocalDatabase
import com.example.database.storage.TeamsStorage
import com.example.domain.repository.CollectionsRepository
import com.example.domain.repository.SensorRepository
import com.example.domain.repository.TeamsRepository
import com.example.reactiveprogramming.di.sensors.ACCELEROMETER_SENSOR
import com.example.reactiveprogramming.di.sensors.BRIGHTNESS_SENSOR
import com.example.reactiveprogramming.di.sensors.ORIENTATION_SENSOR
import com.example.sensors.storage.SensorStorage
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DATABASE_NAME = "localStorage.db"

/**
 * Used to create:
 *  - Database Room instance
 *  - DAO instance for database access
 *  - All the repositories necessary to connect the Use Cases with the Data Store
 *  - All the datastore necessary to access the Sensors, Database and Collections data
 */
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

    single<SensorRepository> { SensorDataRepository(get()) }

    single<CollectionsRepository> { CollectionsDataRepository(get()) }


    /* DATASTORE */

    single<LocalTeamsDatastore> { TeamsStorage(get()) }

    single<SensorDatastore> {
        SensorStorage(
            get(named(BRIGHTNESS_SENSOR)),
            get(named(ORIENTATION_SENSOR)),
            get(named(ACCELEROMETER_SENSOR))
        )
    }

    single<CollectionsDatastore> { CollectionsStorage() }

}