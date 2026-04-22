package com.boss.mygarage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.boss.mygarage.data.local.dao.NoteDao
import com.boss.mygarage.data.local.dao.VehicleDao
import com.boss.mygarage.data.local.entities.NoteEntity
import com.boss.mygarage.data.local.entities.VehicleEntity

@Database(entities = [VehicleEntity::class, NoteEntity::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun noteDao(): NoteDao
}

class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        insertTestData(db)
    }

    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
        super.onDestructiveMigration(db)
    }

    //TODO: Delete in production!
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        db.query("SELECT count(*) FROM vehicles").use { cursor ->
            if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
                insertTestData(db)
            }
        }
    }

    private fun insertTestData(db: SupportSQLiteDatabase) {
        db.execSQL("""
            INSERT INTO vehicles (name, type, metadata)
            VALUES ('Lada Vesta', 'CAR',
            '[{"type": "MILEAGE","customName": null,"value":"45000 км","showOnMain":true}, {"type": "CUSTOM","customName": "Двигатель","value":"1.6","showOnMain":false}]')
        """)
        db.execSQL("""
            INSERT INTO vehicles (name, type, metadata)
            VALUES ('Lada Iskra', 'CAR', '[{"type": "MILEAGE","customName": null,"value":"45000 км","showOnMain":true}]')
        """)
        db.execSQL("""
            INSERT INTO vehicles (name, type, metadata)
            VALUES ('TLC Prado', 'CAR', '[{"type": "MILEAGE","customName": null,"value":"45000 км","showOnMain":true}]')
        """)
        db.execSQL("""
            INSERT INTO vehicles (name, type, metadata)
            VALUES ('Yamaha R6', 'BIKE',
            '[{"type": "MILEAGE","customName": null,"value":"12000 км","showOnMain":true}, {"type": "CUSTOM","customName": "Цепь","value":"Обслужена","showOnMain":true}]')
        """)
        db.execSQL("""
            INSERT INTO vehicles (name, type, metadata)
            VALUES ('Мотоблок Нева', 'TRACTOR',
            '[{"type": "CUSTOM","customName": "Моточасы","value":"12 ч","showOnMain":true}]')
        """)
    }
}