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
enum class VehicleMetricType {
    YEAR,
    MILEAGE,
    COLOR,
    LICENSE_PLATE,
    POWER,
    VIN,
    CUSTOM,
}

@Serializable
enum class VehicleColor {
    BLACK,
    WHITE,
    RED,
    GREEN,
    BLUE,
    YELLOW,
    CYAN,
    MAGENTA,
    GRAY,
    DARK_GRAY,
    LIGHT_GRAY,
    CUSTOM_COLOR
}

enum class MetricValidationError {
    EMPTY_NAME,
    EMPTY_VALUE,
    INVALID_FORMAT
}

@Serializable
data class VehicleMetric(
    val type: VehicleMetricType = VehicleMetricType.CUSTOM,
    val customName: String? = null,
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