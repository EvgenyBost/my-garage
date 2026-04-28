package com.boss.mygarage.data.local.dao

import androidx.room.*
import com.boss.mygarage.data.local.entities.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles ORDER BY name ASC")
    fun getAllVehiclesOrdered(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles WHERE id = :id")
    suspend fun getVehicleById(id: Long): VehicleEntity?

    @Query("SELECT * FROM vehicles WHERE name LIKE :name LIMIT 1")
    fun findVehicleByName(name: String): VehicleEntity

    @Upsert //Update if exist - else insert
    suspend fun upsertVehicle(vehicle: VehicleEntity)

    @Delete
    suspend fun deleteVehicle(vehicle: VehicleEntity)

    @Query("DELETE FROM vehicles WHERE id = :id")
    suspend fun deleteVehicleById(id: Long)
}