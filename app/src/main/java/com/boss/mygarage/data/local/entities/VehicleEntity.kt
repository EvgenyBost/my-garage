package com.boss.mygarage.data.local.entities

import androidx.room.*

@Entity (tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(true) val id: Long = 0,
    val name: String,
    val description: String?, //@ColumnInfo(name = "description")
    val type: String, // CAR, BIKE, TRACTOR...
    val year: Int,
    val iconId: Int,
    val metadata: Map<String, String>
)