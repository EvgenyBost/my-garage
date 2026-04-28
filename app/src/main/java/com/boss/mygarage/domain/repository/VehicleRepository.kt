package com.boss.mygarage.domain.repository

import com.boss.mygarage.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun getAllVehicles(): Flow<List<Vehicle>>
    suspend fun getVehicleById(id: Long): Vehicle?
    suspend fun saveVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicle: Vehicle)
    suspend fun deleteVehicleById(id: Long)
}