package com.boss.mygarage.domain.model
import com.boss.mygarage.domain.model.VehicleMetricType.*

fun Vehicle.validate(): VehicleValidationError? {
    if (name.isBlank()) return VehicleValidationError.EMPTY_VEHICLE_NAME
    return metadata.firstNotNullOfOrNull { it -> validate() }
}

fun VehicleMetric.validate(): VehicleValidationError? {
    return when {
        type == CUSTOM && customName.isNullOrBlank() -> VehicleValidationError.EMPTY_PARAM_NAME
        value.isBlank() -> VehicleValidationError.EMPTY_PARAM_VALUE

        (type in setOf(YEAR, MILEAGE, POWER)) &&
                value.toLongOrNull() == null -> VehicleValidationError.INVALID_PARAM_FORMAT

        (type == COLOR && !VehicleColor.entries.any { it.name.equals(value, ignoreCase = true) }) -> VehicleValidationError.INVALID_PARAM_FORMAT

        else -> null
    }
}

fun Vehicle.getColorMetric(): VehicleMetric? {
    return metadata.find { it.type == COLOR }
}