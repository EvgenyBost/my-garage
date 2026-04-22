package com.boss.mygarage.presentation.common.utils

import androidx.compose.ui.text.input.KeyboardType
import com.boss.mygarage.domain.model.StandardVehicleMetricType.CUSTOM
import com.boss.mygarage.domain.model.StandardVehicleMetricType.MILEAGE
import com.boss.mygarage.domain.model.StandardVehicleMetricType.VIN
import com.boss.mygarage.domain.model.StandardVehicleMetricType.YEAR
import com.boss.mygarage.domain.model.VehicleMetric
import com.boss.mygarage.presentation.edit_vehicle.CustomParamState

fun CustomParamState.getKeyboardTypeForMetric(): KeyboardType {
    val type = this.type

    return when {
        this.type == MILEAGE || this.type == YEAR     -> {
            KeyboardType.Number
        }

        this.type == VIN -> {
            KeyboardType.Ascii
        }

        else -> KeyboardType.Text
    }
}

fun CustomParamState.toVehicleMetric(): VehicleMetric {
    return VehicleMetric(
        type = this.type,
        customName = if (this.type == CUSTOM) this.name.trim() else null,
        value = this.value.trim(),
        showOnMain = this.showOnMain
    )
}

fun VehicleMetric.toCustomParamState(): CustomParamState {
    return CustomParamState(
        type = this.type,
        name = if (this.type == CUSTOM) this.customName?:"" else "",
        value = this.value.trim(),
        showOnMain = this.showOnMain,
        error = null
    )
}