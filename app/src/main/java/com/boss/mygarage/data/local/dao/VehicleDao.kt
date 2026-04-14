package com.boss.mygarage.data.local.dao

import androidx.room.*
import com.boss.mygarage.data.local.entities.VehicleEntity

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles")
    fun getAll(): List<VehicleEntity>

    @Query("SELECT * FROM vehicles WHERE uid IN (:vehicleIds)")
    fun loadAllByIds(vehicleIds: IntArray): List<VehicleEntity>

    @Query("SELECT * FROM vehicles WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): VehicleEntity

    @Insert
    suspend fun insertUser(vehicle: VehicleEntity)

    @Insert
    suspend fun insertAll(vehicles: List<VehicleEntity>)

    @Delete
    fun delete(vehicle: VehicleEntity)
}