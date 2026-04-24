package com.boss.mygarage.presentation.common.utils

import androidx.compose.ui.text.input.KeyboardType
import com.boss.mygarage.domain.model.VehicleMetric
import com.boss.mygarage.domain.model.VehicleMetricType.CUSTOM
import com.boss.mygarage.domain.model.VehicleMetricType.MILEAGE
import com.boss.mygarage.domain.model.VehicleMetricType.POWER
import com.boss.mygarage.domain.model.VehicleMetricType.VIN
import com.boss.mygarage.domain.model.VehicleMetricType.YEAR
import com.boss.mygarage.presentation.edit_vehicle.CustomParamState
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

fun CustomParamState.getKeyboardTypeForMetric(): KeyboardType {
    val type = this.type

    return when (this.type) {
        in setOf(MILEAGE, YEAR, POWER) -> {
            KeyboardType.Number
        }

        VIN -> {
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
        name = if (this.type == CUSTOM) this.customName ?: "" else "",
        value = this.value.trim(),
        showOnMain = this.showOnMain,
        error = null
    )
}

fun capitalizeFirstSymbol(input: String): String {
    if (input.isBlank()) return ""
    return input.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun String.isDate(): Boolean {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return try {
        LocalDate.parse(this, formatter)
        true
    } catch (e: DateTimeParseException) {
        false
    }
}

fun getTodayDate(): String {
    return LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}