package com.boss.mygarage.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class VehicleType {CAR, BIKE, TRACTOR, BICYCLE}

@Serializable
data class VehicleMetric(
    val name: String = "",
    val value: String = "",
    val showOnMain: Boolean = false,
)

data class Vehicle(
    val id: Long,
    val name: String,
    val description: String?,
    val type: VehicleType,
    val metadata: List<VehicleMetric>,
)