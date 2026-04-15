package com.boss.mygarage.di

import androidx.room.Room
import com.boss.mygarage.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    // Create DB singleton
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "my_garage.db").build()
    }

    // Exposing DAOs directly (so that repositories can query them)
    single { get<AppDatabase>().vehicleDao() }
    single { get<AppDatabase>().noteDao() }
}