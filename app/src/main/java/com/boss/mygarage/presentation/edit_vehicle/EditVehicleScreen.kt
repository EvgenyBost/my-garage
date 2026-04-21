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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boss.mygarage.R
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
            text = { Text( stringResource(R.string.cancel_changes_alert_details)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.dismissConfirmationDialog(onCancel)
                }) { Text(stringResource(R.string.button_yes_title)) }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissConfirmationDialog() }) { Text(stringResource(R.string.button_no_title)) }
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
                    IconButton(onClick = { viewModel.saveVehicle(onSuccess = onSave) }, enabled = uiState.hasChanges) {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
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
                // TODO: ADD Exposed Dropdown Menu to choose (VehicleType), add Description
//                OutlinedTextField(
//                    value = uiState.year,
//                    onValueChange = { viewModel.onYearChange(it) },
//                    label = { Text(stringResource(R.string.field_year)) },
//                    modifier = Modifier.fillMaxWidth(),
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
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
                    param = param,
                    onNameChange = { newName -> viewModel.onParamNameChange(param.id, newName) },
                    onValueChange = { newVal -> viewModel.onParamValueChange(param.id, newVal) },
                    onShowOnMainChange = { show -> viewModel.onParamShowChange(param.id, show) }
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
    param: CustomParamState, // Help data class for UI
    onNameChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onShowOnMainChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = 0.5f
            )
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = param.name,
                    onValueChange = onNameChange,
                    label = { Text(stringResource(R.string.param_name)) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = param.value,
                    onValueChange = onValueChange,
                    label = { Text(stringResource(R.string.param_value)) },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = param.showOnMain, onCheckedChange = onShowOnMainChange)
                Text(
                    text = stringResource(R.string.show_on_main),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}