package com.boss.mygarage.presentation.edit_vehicle

import com.boss.mygarage.domain.model.VehicleMetricType
import com.boss.mygarage.domain.model.VehicleType
import com.boss.mygarage.domain.model.VehicleValidationError
import kotlin.random.Random

data class EditVehicleUiState(
    val isNew: Boolean = true,
    val name: String = "",
    val type: VehicleType = VehicleType.CAR,
    val description: String = "",
    val customParams: List<CustomParamState> = emptyList(),
    val showConfirmationDialog: Boolean = false,
    val hasChanges: Boolean = false,
    val error: VehicleValidationError? = null
)

data class CustomParamState(
    val id: Long = Random.nextLong(), // Unique ID for key in LazyColumn
    val name: String = "",
    val value: String = "",
    val showOnMain: Boolean = false,
    val type: VehicleMetricType = VehicleMetricType.CUSTOM,
    val error: VehicleValidationError? = null
)