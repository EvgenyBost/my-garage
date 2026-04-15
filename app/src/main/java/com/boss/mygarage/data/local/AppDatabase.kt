package com.boss.mygarage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.boss.mygarage.data.local.dao.NoteDao
import com.boss.mygarage.data.local.dao.VehicleDao
import com.boss.mygarage.data.local.entities.NoteEntity
import com.boss.mygarage.data.local.entities.VehicleEntity

@Database(entities = [VehicleEntity::class, NoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun noteDao(): NoteDao
}