package com.boss.mygarage.domain.model

data class Note(
    val id: Long,
    val vehicleId: Long,
    val name: String,
    val content: String,
    val timestamp: Long
)
