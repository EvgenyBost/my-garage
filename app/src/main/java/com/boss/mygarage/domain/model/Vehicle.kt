package com.boss.mygarage.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class VehicleType {
    CAR,
    BIKE,
    TRUCK,
    BUS,
    BICYCLE,
    MOPED,
    TRACTOR,
    BOAT,
    ELECTRIC_CAR,
    ELECTRIC_SCOOTER,
    ELECTRIC_BICYCLE,
    SNOWMOBILE,
    OTHER
}

@Serializable
enum class StandardVehicleMetricType {
    MILEAGE,
    COLOR,
    LICENSE_PLATE,
    VIN,
    CUSTOM,
}

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