package com.boss.mygarage.presentation.edit_vehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.model.VehicleMetric
import com.boss.mygarage.domain.usecase.vehicle.GetVehicleByIdUseCase
import com.boss.mygarage.domain.usecase.vehicle.SaveVehicleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditVehicleViewModel(
    private val vehicleId: Long?,
    private val getVehicleByIdUseCase: GetVehicleByIdUseCase,
    private val saveVehicleUseCase: SaveVehicleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditVehicleUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // If ID != null -> edit existing vehicle
        vehicleId?.let { id ->
            viewModelScope.launch {
                getVehicleByIdUseCase(id)?.let { vehicle ->
                    _uiState.update {
                        it.copy(
                            isNew = false,
                            name = vehicle.name,
                            type = vehicle.type,
                            description = vehicle.description ?: "",
                            customParams = vehicle.metadata.map {
                                CustomParamState(
                                    name = it.name,
                                    value = it.value,
                                    showOnMain = it.showOnMain
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    // Add new empty parameters string
    fun addCustomParam() {
        _uiState.update { it.copy(customParams = it.customParams + CustomParamState()) }
    }

    fun onParamNameChange(id: Long, newName: String) {
        updateParam(id) { it.copy(name = newName) }
    }

    fun onParamValueChange(id: Long, newValue: String) {
        updateParam(id) { it.copy(value = newValue) }
    }

    fun onParamShowChange(id: Long, show: Boolean) {
        updateParam(id) { it.copy(showOnMain = show) }
    }

    private fun updateParam(id: Long, transform: (CustomParamState) -> CustomParamState) {
        _uiState.update { state ->
            state.copy(customParams = state.customParams.map {
                if (it.id == id) transform(it) else it
            })
        }
    }

    fun saveVehicle(onSuccess: () -> Unit) {
        val state = _uiState.value

        val vehicleToSave = Vehicle(
            id = vehicleId ?: 0L, // 0 to create, specify to update
            name = state.name,
            type = state.type,
            description = state.description,
            metadata = state.customParams
                .filter { it.name.isNotBlank() }
                .map { param ->
                    VehicleMetric(
                        name = param.name,
                        value = param.value,
                        showOnMain = param.showOnMain
                    )
                }
        )

        viewModelScope.launch {
            saveVehicleUseCase(vehicleToSave)
            onSuccess()
        }
    }
}