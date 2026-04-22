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
    YEAR,
    MILEAGE,
    COLOR,
    LICENSE_PLATE,
    VIN,
    CUSTOM,
}

enum class MetricValidationError {
    EMPTY_NAME,
    EMPTY_VALUE,
    INVALID_FORMAT
}

@Serializable
data class VehicleMetric(
    val type: StandardVehicleMetricType = StandardVehicleMetricType.CUSTOM,
    val customName: String? = null,
    val value: String = "",
    val showOnMain: Boolean = false,
)

fun VehicleMetric.validate(): MetricValidationError? {
    return when {
        type == StandardVehicleMetricType.CUSTOM && customName.isNullOrBlank() -> MetricValidationError.EMPTY_NAME
        value.isBlank() -> MetricValidationError.EMPTY_VALUE

        ( type == StandardVehicleMetricType.YEAR || type == StandardVehicleMetricType.MILEAGE ) &&
                 value.toLongOrNull() == null -> MetricValidationError.INVALID_FORMAT

        else -> null
    }
}

data class Vehicle(
    val id: Long,
    val name: String,
    val description: String?,
    val type: VehicleType,
    val metadata: List<VehicleMetric>,
)