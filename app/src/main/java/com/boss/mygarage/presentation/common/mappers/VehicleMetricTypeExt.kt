package com.boss.mygarage.presentation.common.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.VehicleMetricType
import com.boss.mygarage.domain.model.VehicleMetricType.COLOR
import com.boss.mygarage.domain.model.VehicleMetricType.CUSTOM
import com.boss.mygarage.domain.model.VehicleMetricType.LICENSE_PLATE
import com.boss.mygarage.domain.model.VehicleMetricType.MILEAGE
import com.boss.mygarage.domain.model.VehicleMetricType.VIN
import com.boss.mygarage.domain.model.VehicleMetricType.YEAR

@Composable
fun VehicleMetricType.toDisplayName(): String = when (this) {
    YEAR -> stringResource(R.string.metric_type_year)
    MILEAGE -> stringResource(R.string.metric_type_mileage)
    COLOR -> stringResource(R.string.metric_type_color)
    LICENSE_PLATE -> stringResource(R.string.metric_type_license_plate)
    VIN -> stringResource(R.string.metric_type_vin)
    CUSTOM -> stringResource(R.string.metric_type_custom)
}