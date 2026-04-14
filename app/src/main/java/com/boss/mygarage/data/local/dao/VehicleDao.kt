package com.boss.mygarage.data.local.dao

import androidx.room.*
import com.boss.mygarage.data.local.entities.Vehicle

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles")
    fun getAll(): List<Vehicle>

    @Query("SELECT * FROM vehicles WHERE uid IN (:vehicleIds)")
    fun loadAllByIds(vehicleIds: IntArray): List<Vehicle>

    @Query("SELECT * FROM vehicles WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Vehicle

    @Insert
    suspend fun insertUser(vehicle: Vehicle)

    @Insert
    suspend fun insertAll(vehicles: List<Vehicle>)

    @Delete
    fun delete(vehicle: Vehicle)
}