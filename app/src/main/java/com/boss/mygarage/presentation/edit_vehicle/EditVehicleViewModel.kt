package com.boss.mygarage.presentation.edit_vehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.model.VehicleMetricType
import com.boss.mygarage.domain.model.VehicleType
import com.boss.mygarage.domain.model.validate
import com.boss.mygarage.domain.usecase.vehicle.GetVehicleByIdUseCase
import com.boss.mygarage.domain.usecase.vehicle.SaveVehicleUseCase
import com.boss.mygarage.presentation.common.utils.toCustomParamState
import com.boss.mygarage.presentation.common.utils.toVehicleMetric
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
                            customParams = vehicle.metadata.map { vehicleMetric ->
                                vehicleMetric.toCustomParamState()
                            },
                            hasChanges = false
                        )
                    }
                }
            }
        }
    }

    //Need to call this method if any data has changed
    private fun markHasChanges() {
        if (!_uiState.value.hasChanges) {
            _uiState.update { it.copy(hasChanges = true) }
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
        markHasChanges()
    }

    fun onTypeChange(newType: VehicleType) {
        _uiState.update { it.copy(type = newType) }
        markHasChanges()
    }

    // Add new empty parameters string
    fun addCustomParam() {
        _uiState.update { it.copy(customParams = it.customParams + CustomParamState()) }
        markHasChanges()
    }

    fun onParamNameChange(id: Long, newName: String, newType: VehicleMetricType) {
        updateParam(id) { currentParam ->
            currentParam.copy(
                name = newName,
                type = newType,
                error = null
            )
        }
        markHasChanges()
    }

    fun onParamValueChange(id: Long, newValue: String) {
        updateParam(id) { it.copy(value = newValue, error = null) }
        markHasChanges()
    }

    fun onParamShowChange(id: Long, show: Boolean) {
        updateParam(id) { it.copy(showOnMain = show) }
        markHasChanges()
    }

    fun onParamDelete(id: Long) {
        _uiState.update { state ->
            state.copy(
                customParams = state.customParams.filter { it.id != id }
            )
        }
        markHasChanges()
    }

    private fun updateParam(id: Long, transform: (CustomParamState) -> CustomParamState) {
        _uiState.update { state ->
            state.copy(customParams = state.customParams.map {
                if (it.id == id) transform(it) else it
            })
        }
        markHasChanges()
    }

    private fun saveVehicle(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (!state.hasChanges) {
            onSuccess()
            return
        }

        val vehicleToSave = Vehicle(
            id = vehicleId ?: 0L, // 0 to create, specify to update
            name = state.name,
            type = state.type,
            description = state.description,
            metadata = state.customParams
                .filter { it.name.isNotBlank() || it.type != VehicleMetricType.CUSTOM }
                .map { param ->
                    param.toVehicleMetric()
                }
        )

        viewModelScope.launch {
            saveVehicleUseCase(vehicleToSave)
            onSuccess()
        }
    }

    fun validateAndSave(onSuccess: () -> Unit): Int? {
        val params = _uiState.value.customParams
        var firstErrorIndex: Int? = null

        val validatedParams = params.mapIndexed { index, param ->
            val errorType = param.toVehicleMetric().validate()

            if (errorType != null && firstErrorIndex == null) {
                firstErrorIndex = index
            }
            param.copy(error = errorType)
        }

        _uiState.update { it.copy(customParams = validatedParams) }

        if (firstErrorIndex == null) {
            saveVehicle(onSuccess)
        }
        return firstErrorIndex
    }

    fun onCancelClick(onNavigateBack: () -> Unit) {
        if (_uiState.value.hasChanges) {
            _uiState.update { it.copy(showConfirmationDialog = true) }
        } else {
            onNavigateBack()
        }
    }

    fun dismissConfirmationDialog(action: (() -> Unit)? = null) {
        _uiState.update { it.copy(showConfirmationDialog = false) }
        action?.invoke()
    }
}