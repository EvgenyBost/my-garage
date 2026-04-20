package com.boss.mygarage.presentation.edit_vehicle

import com.boss.mygarage.domain.model.VehicleType
import kotlin.random.Random

data class EditVehicleUiState(
    val isNew: Boolean = true,
    val name: String = "",
    val type: VehicleType = VehicleType.CAR,
    val description: String = "",
    val customParams: List<CustomParamState> = emptyList()
)

data class CustomParamState(
    val id: Long = Random.nextLong(), // Unique ID for key in LazyColumn
    val name: String = "",
    val value: String = "",
    val showOnMain: Boolean = false
)