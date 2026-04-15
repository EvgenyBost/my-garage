package com.boss.mygarage.data.mapper

import com.boss.mygarage.data.local.entities.VehicleEntity
import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.model.VehicleType

// From Room to BL
fun VehicleEntity.toDomain(): Vehicle {
    return Vehicle(
        id = this.id,
        name = this.name,
        type = VehicleType.valueOf(this.type), // String to Enum
        year = this.year,
        description = this.description,
        iconId = this.iconId,
        mainMetrics = this.metadata, // Map<String, String>
    )
}

// From BL to Room
fun Vehicle.toEntity(): VehicleEntity {
    return VehicleEntity(
        id = this.id,
        name = this.name,
        type = this.type.name, // Save Enum as String
        year = this.year,
        description = this.description,
        iconId = this.iconId,
        metadata = this.mainMetrics
    )
}