package com.boss.mygarage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
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

class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Тестовые данные (в формате JSON для метаданных)
        db.execSQL("""
            INSERT INTO vehicles (name, type, year, iconId, metadata) 
            VALUES ('Lada Vesta', 'CAR', 2021, 1, '{"Пробег":"45000 км", "Двигатель":"1.6"}')
        """)
        db.execSQL("""
            INSERT INTO vehicles (name, type, year, iconId, metadata) 
            VALUES ('Yamaha R6', 'BIKE', 2019, 2, '{"Пробег":"12000 км", "Цепь":"Обслужена"}')
        """)
        db.execSQL("""
            INSERT INTO vehicles (name, type, year, iconId, metadata) 
            VALUES ('Мотоблок Нева', 'TRACTOR', 2023, 3, '{"Моточасы":"12 ч", "Фрезы":"Установлены"}')
        """)
    }
}