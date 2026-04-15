package com.boss.mygarage.di

import androidx.room.Room
import com.boss.mygarage.data.local.AppDatabase
import com.boss.mygarage.data.local.DatabaseCallback
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    // Create DB singleton
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "my_garage.db")
            .addCallback(DatabaseCallback()) //to add default items into DB and show it on the main screen
            .build()
    }

    // Exposing DAOs directly (so that repositories can query them)
    single { get<AppDatabase>().vehicleDao() }
    single { get<AppDatabase>().noteDao() }
}