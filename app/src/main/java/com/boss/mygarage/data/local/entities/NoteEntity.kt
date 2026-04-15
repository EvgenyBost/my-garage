package com.boss.mygarage.data.local.entities

import androidx.room.*

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = VehicleEntity::class,
            parentColumns = ["id"],
            childColumns = ["vehicleId"],
            onDelete = ForeignKey.CASCADE //When Vehicle delete -> all associated notes deletes automatically
        )
    ],
    indices = [Index("vehicleId")]
)

data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val noteId: Long = 0,
    val vehicleId: Long,
    val content: String,
    val timestamp: Long,
    val name: String
)