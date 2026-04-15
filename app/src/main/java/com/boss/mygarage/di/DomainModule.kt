package com.boss.mygarage.di

import com.boss.mygarage.domain.usecase.note.DeleteNoteUseCase
import com.boss.mygarage.domain.usecase.note.GetNoteByIdUseCase
import com.boss.mygarage.domain.usecase.note.GetNotesForVehicleUseCase
import com.boss.mygarage.domain.usecase.note.SaveNoteUseCase
import com.boss.mygarage.domain.usecase.vehicle.DeleteVehicleUseCase
import com.boss.mygarage.domain.usecase.vehicle.GetAllVehiclesUseCase
import com.boss.mygarage.domain.usecase.vehicle.GetVehicleByIdUseCase
import com.boss.mygarage.domain.usecase.vehicle.SaveVehicleUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAllVehiclesUseCase( repository = get()) }
    factory { GetVehicleByIdUseCase(repository = get()) }
    factory { SaveVehicleUseCase(repository = get()) }
    factory { DeleteVehicleUseCase(repository = get()) }
}

//factory { GetNotesForVehicleUseCase = get() }
//factory { GetNoteByIdUseCase = get() }
//factory { SaveNoteUseCase = get() }
//factory { DeleteNoteUseCase = get() }