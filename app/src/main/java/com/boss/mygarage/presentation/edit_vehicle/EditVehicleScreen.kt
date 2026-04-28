package com.boss.mygarage.presentation.edit_vehicle

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.VehicleColor
import com.boss.mygarage.domain.model.VehicleMetricType
import com.boss.mygarage.domain.model.VehicleMetricType.COLOR
import com.boss.mygarage.domain.model.VehicleMetricType.CUSTOM
import com.boss.mygarage.domain.model.VehicleMetricType.INSURANCE_EXPIRED
import com.boss.mygarage.domain.model.VehicleMetricType.LICENSE_PLATE
import com.boss.mygarage.domain.model.VehicleMetricType.MILEAGE
import com.boss.mygarage.domain.model.VehicleMetricType.POWER
import com.boss.mygarage.domain.model.VehicleMetricType.VIN
import com.boss.mygarage.domain.model.VehicleMetricType.YEAR
import com.boss.mygarage.domain.model.VehicleMetricType.entries
import com.boss.mygarage.domain.model.VehicleType
import com.boss.mygarage.domain.model.VehicleValidationError
import com.boss.mygarage.presentation.common.mappers.toColor
import com.boss.mygarage.presentation.common.mappers.toDisplayMessage
import com.boss.mygarage.presentation.common.mappers.toDisplayName
import com.boss.mygarage.presentation.common.mappers.toVehicleColorOrNull
import com.boss.mygarage.presentation.common.utils.capitalizeFirstSymbol
import com.boss.mygarage.presentation.common.utils.convertMillisToDate
import com.boss.mygarage.presentation.common.utils.getKeyboardTypeForMetric
import com.boss.mygarage.presentation.common.utils.getTodayDate
import com.boss.mygarage.presentation.common.utils.isDate
import com.boss.mygarage.presentation.main.components.SwipeToDeleteContainer
import com.boss.mygarage.presentation.main.components.VehicleIconBoxWithoutBackground
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditVehicleScreen(
    onSave: () -> Unit,
    onCancel: () -> Unit,
    viewModel: EditVehicleViewModel = koinViewModel()
) {
    // Get state from ViewModel (Need to create special fields in VM)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Intercept "Back" button click
    BackHandler(enabled = uiState.hasChanges) {
        viewModel.onCancelClick(onCancel)
    }

    // Cancel changes confirmation dialog
    if (uiState.showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissConfirmationDialog() },
            title = { Text(stringResource(R.string.cancel_changes_alert_title)) },
            text = { Text(stringResource(R.string.cancel_changes_alert_details)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.dismissConfirmationDialog(onCancel)
                }) { Text(stringResource(R.string.button_yes_title)) }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissConfirmationDialog() }) {
                    Text(
                        stringResource(R.string.button_no_title)
                    )
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.isNew) {
                            stringResource(R.string.add_vehicle_title)
                        } else {
                            stringResource(R.string.edit_vehicle_title)
                        }
                    )
                },

                actions = {
                    IconButton(onClick = { viewModel.onCancelClick(onCancel) }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.cancel_button_description)
                        )
                    }
                    IconButton(
                        onClick = { viewModel.validateAndSave(onSuccess = onSave) },
                        enabled = uiState.hasChanges
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        val paramNameToTypeMap = entries.associateBy { type ->
            stringResource(
                when (type) {
                    YEAR -> R.string.metric_type_year
                    MILEAGE -> R.string.metric_type_mileage
                    COLOR -> R.string.metric_type_color
                    LICENSE_PLATE -> R.string.metric_type_license_plate
                    POWER -> R.string.metric_type_power
                    VIN -> R.string.metric_type_vin
                    INSURANCE_EXPIRED -> R.string.metric_type_insurance_expires
                    CUSTOM -> R.string.metric_type_custom
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Main field
            item {
                val isParamNameError =
                    (uiState.error != null && uiState.error == VehicleValidationError.EMPTY_VEHICLE_NAME)

                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    label = { Text(stringResource(R.string.field_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = isParamNameError,
                    supportingText = {
                        Text(text = uiState.error?.toDisplayMessage() ?: "")
                    }
                )
            }

            item {
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = uiState.type.toDisplayName(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.field_type)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        leadingIcon = {
                            VehicleIconBoxWithoutBackground(uiState.type)
                        },
                        modifier = Modifier
                            .menuAnchor(
                                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            )
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        VehicleType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = type.toDisplayName())
                                        VehicleIconBoxWithoutBackground(type)
                                    }
                                },
                                onClick = {
                                    viewModel.onTypeChange(type)
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = stringResource(R.string.additional_params),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            val alreadyExistsParams = uiState.customParams.map { it.type }.toSet()

            // Dynamic list with custom params
            items(
                items = uiState.customParams,
                key = { it.id }) { param ->
                CustomParamCell(
                    modifier = Modifier.animateItem(),
                    param = param,
                    onNameChange = { newName ->
                        // if User enter "cOlOR" -> Save as "Color"
                        val correctName = capitalizeFirstSymbol(newName)
                        val newType = paramNameToTypeMap[correctName.trim()] ?: CUSTOM

                        viewModel.onParamNameChange(param.id, correctName, newType, param.type)
                    },
                    onValueChange = { newVal -> viewModel.onParamValueChange(param.id, newVal) },
                    onShowOnMainChange = { show -> viewModel.onParamShowChange(param.id, show) },
                    onDeleteConfirm = { viewModel.onParamDelete(param.id) },
                    existingParams = alreadyExistsParams,
                )
            }

            item {
                TextButton(
                    onClick = { viewModel.addCustomParam() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.add_param_button))
                }
            }
        }
    }
}

@Composable
fun CustomParamCell(
    modifier: Modifier = Modifier,
    param: CustomParamState,
    onNameChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onShowOnMainChange: (Boolean) -> Unit,
    onDeleteConfirm: () -> Unit,
    existingParams: Set<VehicleMetricType>,
) {
    SwipeToDeleteContainer(
        deleteDialogTitle = stringResource(R.string.delete_parameter_alert_title),
        deleteDialogText = stringResource(R.string.delete_parameter_alert_details),
        onDeleteClick = onDeleteConfirm,
        content = {
            val isError = param.error != null

            Card(
                modifier = modifier.fillMaxWidth(),
                shape = CardDefaults.shape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isError)
                        MaterialTheme.colorScheme.errorContainer
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        var paramName =
                            if (param.type == CUSTOM) param.name else param.type.toDisplayName()
                        if (param.name.trim() == paramName) paramName =
                            param.name //when User need to enter ex. "Color of wheels"

                        MetricNameInputField(
                            value = paramName,
                            onValueChange = onNameChange,
                            modifier = Modifier.weight(1f),
                            error = param.error,
                            existingParams = existingParams
                        )

                        val isParamValueError = (isError && (
                                param.error == VehicleValidationError.INVALID_PARAM_FORMAT
                                        || param.error == VehicleValidationError.EMPTY_PARAM_VALUE))

                        when (param.type) {
                            COLOR -> VehicleColorDropdownMenu(
                                param = param,
                                onValueChange = onValueChange,
                                modifier = Modifier.weight(1f),
                            )

                            INSURANCE_EXPIRED -> ParamValueDatePicker(
                                modifier = Modifier.weight(1f),
                                param = param,
                                isParamValueError = isParamValueError,
                                onValueChange = onValueChange
                            )

                            else ->
                                OutlinedTextField(
                                    value = param.value,
                                    onValueChange = onValueChange,
                                    label = { Text(stringResource(R.string.param_value)) },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true,
                                    maxLines = 1,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = param.getKeyboardTypeForMetric(),
                                        imeAction = ImeAction.Done
                                    ),
                                    isError = isParamValueError,
                                    supportingText = {
                                        if (isParamValueError) {
                                            Text(text = param.error.toDisplayMessage())
                                        }
                                    }
                                )
                        }

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(checked = param.showOnMain, onCheckedChange = onShowOnMainChange)
                        Text(
                            text = stringResource(R.string.show_on_main),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    );


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetricNameInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    existingParams: Set<VehicleMetricType>,
    error: VehicleValidationError?

) {
    var expanded by remember { mutableStateOf(false) }

    // Get all standard param names (except CUSTOM and already exist params)
    val standardNames = entries
        .filter { it != CUSTOM && it !in existingParams }
        .map { it.toDisplayName() }

    val isParamNameError = error == VehicleValidationError.EMPTY_PARAM_NAME

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                expanded = true
            },
            label = { Text(stringResource(R.string.param_name)) },
            modifier = Modifier
                .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            isError = isParamNameError,
            supportingText = {
                if (isParamNameError) {
                    Text(text = error.toDisplayMessage())
                }
            },
        )

        val filteredOptions = standardNames.filter {
            it.contains(value, ignoreCase = true)
        }

        if (filteredOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filteredOptions.forEach { name ->
                    DropdownMenuItem(
                        text = { Text(name) },
                        onClick = {
                            onValueChange(name)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleColorDropdownMenu(
    param: CustomParamState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val isParamValueError = param.error != null

    val selectedColor = param.value.toVehicleColorOrNull() ?: VehicleColor.CUSTOM_COLOR

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedColor.toDisplayName(),
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.param_value)) },
            modifier = Modifier
                .menuAnchor(
                    type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            leadingIcon = {
                ColorIndicator(color = selectedColor.toColor())
            },
            isError = isParamValueError,
            supportingText = {
                if (isParamValueError) {
                    Text(text = param.error.toDisplayMessage())
                }
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            VehicleColor.entries.forEach { colorOption ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = colorOption.toDisplayName())
                            ColorIndicator(color = colorOption.toColor())
                        }
                    },
                    onClick = {
                        onValueChange(colorOption.name)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}


@Composable
fun ColorIndicator(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(color)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), CircleShape)
    )
}

@Composable
fun ParamValueDatePicker(
    modifier: Modifier = Modifier,
    param: CustomParamState,
    isParamValueError: Boolean,
    onValueChange: (String) -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedDate = datePickerState.selectedDateMillis
                    if (selectedDate != null) {
                        val formattedDate = convertMillisToDate(selectedDate)
                        onValueChange(formattedDate)
                    }
                    showDatePicker = false
                }) { Text(stringResource(R.string.ok_button_title)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) { Text(stringResource(R.string.cancel_button_title)) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    val correctDateValue: String
    if (param.value.isDate()) correctDateValue = param.value
    else {
        correctDateValue = getTodayDate()
        onValueChange(correctDateValue)
    }

    OutlinedTextField(
        value = correctDateValue,
        onValueChange = { },
        readOnly = true,
        label = { Text(stringResource(R.string.param_value)) },
        modifier = modifier.clickable { showDatePicker = true },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        singleLine = true,
        isError = isParamValueError,
        supportingText = {
            if (isParamValueError) {
                Text(text = param.error?.toDisplayMessage() ?: "")
            }
        },
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.select_date_title)
                )
            }
        }
    )
}