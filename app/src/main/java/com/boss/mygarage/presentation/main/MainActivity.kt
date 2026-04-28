package com.boss.mygarage.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.boss.mygarage.R
import com.boss.mygarage.presentation.common.navigation.NavGraph
import com.boss.mygarage.presentation.common.theme.MyGarageTheme
import com.boss.mygarage.presentation.main.components.VehicleItem
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyGarageTheme {
                val navController = rememberNavController()

                //MainActivity just setup container for NavGraph
                NavGraph(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    viewModel: MainViewModel = koinViewModel(),
) {
    val vehicles by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.main_screen_title)) },
                actions = {
                    IconButton(onClick = onAddClick) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_vehicle_content_description),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(
                items = vehicles,
                key = { it.id },
            ) { vehicle ->
                VehicleItem(
                    vehicle = vehicle,
                    onEditClick = { onEditClick(vehicle.id) },
                    onDeleteClick = { onDeleteClick(vehicle.id) },
                    onCardClick = {},
                )
            }
        }
    }
}