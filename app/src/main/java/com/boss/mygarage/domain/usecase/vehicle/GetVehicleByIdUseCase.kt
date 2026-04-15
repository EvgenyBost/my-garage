package com.boss.mygarage.domain.usecase.vehicle

import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.repository.VehicleRepository

class GetVehicleByIdUseCase (private val repository: VehicleRepository) {
    suspend operator fun invoke(id: Long): Vehicle? = repository.getVehicleById(id)
}