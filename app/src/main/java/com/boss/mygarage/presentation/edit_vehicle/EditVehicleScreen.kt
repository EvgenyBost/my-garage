package com.boss.mygarage.presentation.edit_vehicle

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.MetricValidationError
import com.boss.mygarage.domain.model.StandardVehicleMetricType
import com.boss.mygarage.domain.model.StandardVehicleMetricType.*
import com.boss.mygarage.domain.model.VehicleType
import com.boss.mygarage.presentation.common.mappers.toDisplayMessage
import com.boss.mygarage.presentation.common.mappers.toDisplayName
import com.boss.mygarage.presentation.common.utils.getKeyboardTypeForMetric
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
                        onClick = { viewModel.validateAndSave (onSuccess = onSave) },
                        enabled = uiState.hasChanges
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        val context = LocalContext.current

        val paramNameToTypeMap = remember(context) {
            StandardVehicleMetricType.entries.associateBy { type ->
                context.getString(when(type) {
                    YEAR -> R.string.metric_type_year
                    MILEAGE -> R.string.metric_type_mileage
                    COLOR -> R.string.metric_type_color
                    LICENSE_PLATE -> R.string.metric_type_license_plate
                    VIN -> R.string.metric_type_vin
                    CUSTOM -> R.string.metric_type_custom
                })
            }
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
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    label = { Text(stringResource(R.string.field_name)) },
                    modifier = Modifier.fillMaxWidth()
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
                                text = { Text(text = type.toDisplayName()) },
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

            // Dynamic list with custom params
            items(
                items = uiState.customParams,
                key = { it.id }) { param ->
                CustomParamCell(
                    modifier = Modifier.animateItem(),
                    param = param,
                    onNameChange = { newName ->
                        val newType = paramNameToTypeMap[newName.trim()] ?: CUSTOM
                        viewModel.onParamNameChange(param.id, newName, newType)
                    },
                    onValueChange = { newVal -> viewModel.onParamValueChange(param.id, newVal) },
                    onShowOnMainChange = { show -> viewModel.onParamShowChange(param.id, show) },
                    onDeleteConfirm = { viewModel.onParamDelete(param.id) },
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
    param: CustomParamState, // Help data class for UI
    onNameChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onShowOnMainChange: (Boolean) -> Unit,
    onDeleteConfirm: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val isError = param.error != null

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isError)
                MaterialTheme.colorScheme.errorContainer
            else
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val paramName = if(param.type == CUSTOM) param.name else param.type.toDisplayName()

                MetricNameInputField(
                    value = paramName,
                    onValueChange = onNameChange,
                    modifier = Modifier.weight(1f),
                    error = param.error
                )

                val isParamValueError = (isError && (
                        param.error == MetricValidationError.INVALID_FORMAT
                        || param.error == MetricValidationError.EMPTY_VALUE))

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
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_button_description),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text(stringResource(R.string.delete_parameter_alert_title)) },
                text = { Text(stringResource(R.string.delete_parameter_alert_details)) },
                confirmButton = {
                    TextButton(onClick = {
                        onDeleteConfirm()
                        showDeleteDialog = false
                    }) {
                        Text(
                            stringResource(R.string.delete_button_title),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text(stringResource(R.string.cancel_button_title))
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetricNameInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    error: MetricValidationError?

) {
    var expanded by remember { mutableStateOf(false) }

    // Get all standard param names (except CUSTOM)
    val standardNames = StandardVehicleMetricType.entries
        .filter { it != StandardVehicleMetricType.CUSTOM }
        .map { it.toDisplayName() }

    val isParamNameError = error == MetricValidationError.EMPTY_NAME

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