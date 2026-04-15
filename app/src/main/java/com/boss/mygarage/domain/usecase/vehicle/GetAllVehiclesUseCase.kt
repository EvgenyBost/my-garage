package com.boss.mygarage.domain.usecase.vehicle

import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow

class GetAllVehiclesUseCase(private val repository: VehicleRepository) {
    operator fun invoke(): Flow<List<Vehicle>> = repository.getAllVehicles()
}