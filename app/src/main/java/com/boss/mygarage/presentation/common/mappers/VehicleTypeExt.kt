package com.boss.mygarage.presentation.common.mappers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ElectricBike
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Snowmobile
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.VehicleType
import com.boss.mygarage.domain.model.VehicleType.*

@Composable
fun VehicleType.toDisplayName(): String = when (this) {
    CAR -> stringResource(R.string.type_car)
    BIKE -> stringResource(R.string.type_bike)
    TRACTOR -> stringResource(R.string.type_tractor)
    BICYCLE -> stringResource(R.string.type_bicycle)
    TRUCK -> stringResource(R.string.type_truck)
    BUS -> stringResource(R.string.type_bus)
    BOAT -> stringResource(R.string.type_boat)
    ELECTRIC_CAR -> stringResource(R.string.type_electric_car)
    MOPED -> stringResource(R.string.type_moped)
    ELECTRIC_SCOOTER -> stringResource(R.string.type_electric_scooter)
    ELECTRIC_BICYCLE -> stringResource(R.string.type_electric_bicycle)
    SNOWMOBILE -> stringResource(R.string.type_snowmobile)
    OTHER -> stringResource(R.string.type_other)
}

@Composable
fun VehicleType.toIcon() = when (this) {
    CAR -> Icons.Default.DirectionsCar
    BIKE -> Icons.Default.TwoWheeler
    TRACTOR -> Icons.Default.Agriculture
    BICYCLE -> Icons.Default.PedalBike
    TRUCK -> Icons.Default.LocalShipping
    BUS -> Icons.Default.AirportShuttle
    BOAT -> Icons.Default.DirectionsBoat
    ELECTRIC_CAR -> Icons.Default.ElectricCar
    MOPED -> Icons.Default.Moped
    ELECTRIC_SCOOTER -> Icons.Default.ElectricScooter
    ELECTRIC_BICYCLE -> Icons.Default.ElectricBike
    SNOWMOBILE -> Icons.Default.Snowmobile
    OTHER -> Icons.Default.Handyman
}