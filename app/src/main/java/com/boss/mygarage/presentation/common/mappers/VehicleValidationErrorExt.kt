package com.boss.mygarage.presentation.common.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.VehicleValidationError

@Composable
fun VehicleValidationError.toDisplayMessage(): String {
    val resId = when (this) {
        VehicleValidationError.EMPTY_PARAM_NAME -> R.string.vehicle_validation_error_empty_param_name
        VehicleValidationError.EMPTY_PARAM_VALUE -> R.string.vehicle_validation_error_empty_param_value
        VehicleValidationError.INVALID_PARAM_FORMAT -> R.string.vehicle_validation_error_invalid_param_format
        VehicleValidationError.EMPTY_VEHICLE_NAME -> R.string.vehicle_validation_error_empty_name
    }
    return stringResource(resId)
}