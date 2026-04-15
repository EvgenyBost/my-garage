package com.boss.mygarage.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.usecase.vehicle.DeleteVehicleUseCase
import com.boss.mygarage.domain.usecase.vehicle.GetAllVehiclesUseCase
import com.boss.mygarage.domain.usecase.vehicle.GetVehicleByIdUseCase
import com.boss.mygarage.domain.usecase.vehicle.SaveVehicleUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val getAllVehiclesUseCase: GetAllVehiclesUseCase,
    private val getVehicleByIdUseCase: GetVehicleByIdUseCase,
    private val saveVehicleUseCase: SaveVehicleUseCase,
    private val deleteVehicleUseCase: DeleteVehicleUseCase,
) : ViewModel() {

    // Transform Flow from UseCase to StateFlow, to use in Compose.
    // stateIn makes the thread "hot": it lives as long as the ViewModel is alive.
    val uiState: StateFlow<List<Vehicle>> = getAllVehiclesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Pause 5s.
            //If the user returns to the screen within 5 seconds, the stream will not restart
            //and the data will not be downloaded again. This prevents unnecessary reloads when rotating the screen.
            initialValue = emptyList()
        )

    fun onEditClick(vehicle: Vehicle) {
        // Show details screen
    }

    fun onVehicleClick(vehicle: Vehicle) {
        // Show details screen
    }
}