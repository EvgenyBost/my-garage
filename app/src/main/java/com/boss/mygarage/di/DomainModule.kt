package com.boss.mygarage.di

import com.boss.mygarage.domain.usecase.GetAllVehiclesUseCase
import com.boss.mygarage.domain.usecase.GetVehicleDetailsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAllVehiclesUseCase(get()) }
    //factory { GetVehicleDetailsUseCase(get()) }

    //factory { AddNoteUseCase(get()) }
}