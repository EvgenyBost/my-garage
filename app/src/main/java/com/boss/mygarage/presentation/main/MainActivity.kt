package com.boss.mygarage.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boss.mygarage.presentation.common.theme.MyGarageTheme
import com.boss.mygarage.presentation.main.components.VehicleItem
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyGarageTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel(),
               modifier: Modifier = Modifier) {
    // Subscribe to the state. Compose will redraw the screen every time something changes in the equipment list.
    val vehicles by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(vehicles, key = { it.id }) { vehicle ->
            VehicleItem(
                vehicle = vehicle,
                onEditClick = { viewModel.onEditClick(vehicle) },
                onCardClick = {},
                modifier,
            )
        }
    }
}