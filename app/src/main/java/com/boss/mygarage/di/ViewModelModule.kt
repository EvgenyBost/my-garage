package com.boss.mygarage.di

import com.boss.mygarage.presentation.edit_vehicle.EditVehicleViewModel
import com.boss.mygarage.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(
        getAllVehiclesUseCase = get(),
        getVehicleByIdUseCase = get(),
        saveVehicleUseCase = get(),
        deleteVehicleUseCase = get(),
    ) }

    viewModel { (vehicleId: Long?) ->
        EditVehicleViewModel(
            vehicleId = vehicleId,
            getVehicleByIdUseCase = get(),
            saveVehicleUseCase = get(),
        ) }

//    viewModel { (vehicleId: Long) ->
//        DetailsViewModel(vehicleId, get(), get())
//    }
}