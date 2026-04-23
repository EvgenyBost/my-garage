package com.boss.mygarage.domain.model

fun VehicleMetric.validate(): MetricValidationError? {
    return when {
        type == VehicleMetricType.CUSTOM && customName.isNullOrBlank() -> MetricValidationError.EMPTY_NAME
        value.isBlank() -> MetricValidationError.EMPTY_VALUE

        (type == VehicleMetricType.YEAR || type == VehicleMetricType.MILEAGE) &&
                value.toLongOrNull() == null -> MetricValidationError.INVALID_FORMAT

        (type == VehicleMetricType.COLOR && !VehicleColor.entries.any { it.name == value }) -> MetricValidationError.INVALID_FORMAT

        else -> null
    }
}

fun Vehicle.getColorMetric(): VehicleMetric? {
    return metadata.find { it.type == VehicleMetricType.COLOR }
}