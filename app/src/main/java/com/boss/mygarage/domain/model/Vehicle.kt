package com.boss.mygarage.domain.model

enum class VehicleType {CAR, BIKE, TRACTOR, BICYCLE}

data class Vehicle(
    val id: Long,
    val name: String,
    val description: String?,
    val type: VehicleType,
    val year: Int,
    val iconId: Int,
    val mainMetrics: Map<String, String> // Ex: "Mileage" -> "12000"
)