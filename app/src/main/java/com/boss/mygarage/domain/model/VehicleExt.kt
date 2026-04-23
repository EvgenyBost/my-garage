package com.boss.mygarage.domain.model
import com.boss.mygarage.domain.model.VehicleMetricType.*

fun VehicleMetric.validate(): MetricValidationError? {
    return when {
        type == CUSTOM && customName.isNullOrBlank() -> MetricValidationError.EMPTY_NAME
        value.isBlank() -> MetricValidationError.EMPTY_VALUE

        (type in setOf(YEAR, MILEAGE, POWER)) &&
                value.toLongOrNull() == null -> MetricValidationError.INVALID_FORMAT

        (type == COLOR && !VehicleColor.entries.any { it.name == value }) -> MetricValidationError.INVALID_FORMAT

        else -> null
    }
}

fun Vehicle.getColorMetric(): VehicleMetric? {
    return metadata.find { it.type == COLOR }
}