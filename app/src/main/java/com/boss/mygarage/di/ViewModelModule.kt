package com.boss.mygarage.di

import com.boss.mygarage.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }

//    viewModel { (vehicleId: Long) ->
//        DetailsViewModel(vehicleId, get(), get())
//    }
}