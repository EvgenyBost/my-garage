package com.boss.mygarage.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.model.VehicleColor
import com.boss.mygarage.domain.model.VehicleType
import com.boss.mygarage.domain.model.getColorMetric
import com.boss.mygarage.presentation.common.mappers.toColor
import com.boss.mygarage.presentation.common.mappers.toIcon
import com.boss.mygarage.presentation.common.mappers.toVehicleColorOrNull

@Composable
fun VehicleIconBox(vehicleType: VehicleType, vehicleColor: VehicleColor, needBackground: Boolean) {

    Box(
        Modifier
            .size(48.dp)
            .then(
                if (needBackground) Modifier.background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = vehicleType.toIcon(),
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.3f),
            modifier = Modifier
                .offset(x = 1.5.dp, y = 1.5.dp)
        )

        Icon(
            imageVector = vehicleType.toIcon(),
            contentDescription = null,
            tint = vehicleColor.toColor(),
        )
    }
}

@Composable
fun VehicleIconBoxWithoutBackground(vehicleType: VehicleType) {
    VehicleIconBox(vehicleType, VehicleColor.CUSTOM_COLOR, false)
}

@Composable
fun VehicleIconBox(vehicle: Vehicle) {

    val selectedColor = vehicle.getColorMetric()?.value?.toVehicleColorOrNull()
        ?: VehicleColor.CUSTOM_COLOR

    VehicleIconBox(vehicle.type, selectedColor, true)
}