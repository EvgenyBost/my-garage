package com.boss.mygarage.presentation.common.navigation

import kotlinx.serialization.Serializable

// Each object is a route. If we need to pass params to Screen, we should use data class instead of object.
@Serializable
sealed interface Screen {
    @Serializable
    data object Main : Screen

    @Serializable
    data class EditVehicle(val vehicleId: Long? = null) : Screen // null = create new
}