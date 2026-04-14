package com.boss.mygarage.data.local.entities

import androidx.room.*

@Entity (tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(true) val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?
)