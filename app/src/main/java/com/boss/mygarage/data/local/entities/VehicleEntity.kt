package com.boss.mygarage.data.local.entities

import androidx.room.*
import com.boss.mygarage.domain.model.VehicleMetric
import com.boss.mygarage.domain.model.VehicleType

@Entity (tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(true) val id: Long = 0,
    val name: String,
    val description: String?, //@ColumnInfo(name = "description")
    val type: VehicleType, // CAR, BIKE, TRACTOR...
    val metadata: List<VehicleMetric>
)