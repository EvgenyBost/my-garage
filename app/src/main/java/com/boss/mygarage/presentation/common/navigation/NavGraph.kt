package com.boss.mygarage.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.boss.mygarage.presentation.edit_vehicle.EditVehicleScreen
import com.boss.mygarage.presentation.main.MainScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main,
        modifier = modifier
    ) {
        // Main Screen
        composable<Screen.Main> {
            MainScreen(
                onAddClick = {
                    navController.navigate(Screen.EditVehicle(null))
                },
                onEditClick = { vehicleId ->
                    navController.navigate(Screen.EditVehicle(vehicleId))
                },
            )
        }

        // Add Vehicle Screen
        composable<Screen.EditVehicle> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.EditVehicle>()

            EditVehicleScreen(
                viewModel = koinViewModel { parametersOf(args.vehicleId) },
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}