package com.boss.mygarage.presentation.common.mappers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.VehicleType

@Composable
fun VehicleType.toDisplayName(): String = when (this) {
    VehicleType.CAR -> stringResource(R.string.type_car)
    VehicleType.BIKE -> stringResource(R.string.type_bike)
    VehicleType.TRACTOR -> stringResource(R.string.type_tractor)
    VehicleType.BICYCLE -> stringResource(R.string.type_bicycle)
}

@Composable
fun VehicleType.toIcon() = when (this) {
    VehicleType.CAR -> Icons.Default.DirectionsCar
    VehicleType.BIKE -> Icons.Default.TwoWheeler
    VehicleType.TRACTOR -> Icons.Default.Agriculture
    VehicleType.BICYCLE -> Icons.Default.PedalBike
}