package com.boss.mygarage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boss.mygarage.data.local.dao.VehicleDao
import com.boss.mygarage.data.local.entities.Vehicle

@Database(entities = [Vehicle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}