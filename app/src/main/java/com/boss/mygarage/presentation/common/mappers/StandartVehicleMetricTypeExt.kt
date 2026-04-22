package com.boss.mygarage.presentation.common.mappers

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.StandardVehicleMetricType
import com.boss.mygarage.domain.model.StandardVehicleMetricType.COLOR
import com.boss.mygarage.domain.model.StandardVehicleMetricType.CUSTOM
import com.boss.mygarage.domain.model.StandardVehicleMetricType.LICENSE_PLATE
import com.boss.mygarage.domain.model.StandardVehicleMetricType.MILEAGE
import com.boss.mygarage.domain.model.StandardVehicleMetricType.VIN
import com.boss.mygarage.domain.model.StandardVehicleMetricType.YEAR

@Composable
fun StandardVehicleMetricType.toDisplayName(): String = when (this) {
    YEAR -> stringResource(R.string.metric_type_year)
    MILEAGE -> stringResource(R.string.metric_type_mileage)
    COLOR -> stringResource(R.string.metric_type_color)
    LICENSE_PLATE -> stringResource(R.string.metric_type_license_plate)
    VIN -> stringResource(R.string.metric_type_vin)
    CUSTOM -> stringResource(R.string.metric_type_custom)
}