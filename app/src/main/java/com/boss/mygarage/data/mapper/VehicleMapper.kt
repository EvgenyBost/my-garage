package com.boss.mygarage.data.mapper

import com.boss.mygarage.data.local.entities.VehicleEntity
import com.boss.mygarage.domain.model.Vehicle

// From Room to BL
fun VehicleEntity.toDomain(): Vehicle {
    return Vehicle(
        id = this.id,
        name = this.name,
        type = this.type,
        description = this.description,
        metadata = this.metadata,
    )
}

// From BL to Room
fun Vehicle.toEntity(): VehicleEntity {
    return VehicleEntity(
        id = this.id,
        name = this.name,
        type = this.type,
        description = this.description,
        metadata = this.metadata,
    )
}