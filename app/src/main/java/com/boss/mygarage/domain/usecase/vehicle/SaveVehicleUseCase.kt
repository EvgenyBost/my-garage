package com.boss.mygarage.domain.usecase.vehicle

import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.repository.VehicleRepository

class SaveVehicleUseCase(private val repository: VehicleRepository) {
    suspend operator fun invoke(vehicle: Vehicle) = repository.saveVehicle(vehicle)
}