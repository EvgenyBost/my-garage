package com.boss.mygarage.presentation.common.mappers

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.VehicleColor

fun String.toVehicleColorOrNull(): VehicleColor? {
    return VehicleColor.entries.find { it.name == this.uppercase().trim() }
}

@Composable
fun VehicleColor.toDisplayName(): String = when (this) {
    VehicleColor.BLACK -> stringResource(R.string.color_black)
    VehicleColor.WHITE -> stringResource(R.string.color_white)
    VehicleColor.RED -> stringResource(R.string.color_red)
    VehicleColor.GREEN -> stringResource(R.string.color_green)
    VehicleColor.BLUE -> stringResource(R.string.color_blue)
    VehicleColor.YELLOW -> stringResource(R.string.color_yellow)
    VehicleColor.CYAN -> stringResource(R.string.color_cyan)
    VehicleColor.MAGENTA -> stringResource(R.string.color_magenta)
    VehicleColor.GRAY -> stringResource(R.string.color_gray)
    VehicleColor.DARK_GRAY -> stringResource(R.string.color_darkgray)
    VehicleColor.LIGHT_GRAY -> stringResource(R.string.color_lightgray)
    VehicleColor.CUSTOM_COLOR -> stringResource(R.string.color_custom)
}

@Composable
fun VehicleColor.toColor(): Color = when (this) {
    VehicleColor.BLACK -> Color.Black
    VehicleColor.WHITE -> Color.White
    VehicleColor.RED -> Color.Red
    VehicleColor.GREEN -> Color.Green
    VehicleColor.BLUE -> Color.Blue
    VehicleColor.YELLOW -> Color.Yellow
    VehicleColor.CYAN -> Color.Cyan
    VehicleColor.MAGENTA -> Color.Magenta
    VehicleColor.GRAY -> Color.Gray
    VehicleColor.DARK_GRAY -> Color.DarkGray
    VehicleColor.LIGHT_GRAY -> Color.LightGray
    VehicleColor.CUSTOM_COLOR -> MaterialTheme.colorScheme.onPrimaryContainer
}