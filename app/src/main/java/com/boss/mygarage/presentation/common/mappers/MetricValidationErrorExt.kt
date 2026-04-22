package com.boss.mygarage.presentation.common.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.MetricValidationError

@Composable
fun MetricValidationError.toDisplayMessage(): String {
    val resId = when (this) {
        MetricValidationError.EMPTY_NAME -> R.string.metric_validation_error_empty_name
        MetricValidationError.EMPTY_VALUE -> R.string.metric_validation_error_empty_value
        MetricValidationError.INVALID_FORMAT -> R.string.metric_validation_error_invalid_format
    }
    return stringResource(resId)
}