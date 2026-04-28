package com.boss.mygarage.domain.usecase.vehicle

import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.repository.VehicleRepository

class DeleteVehicleUseCase(private val repository: VehicleRepository) {
    suspend operator fun invoke(vehicleId: Long) {
        repository.deleteVehicleById(vehicleId)
    }
}