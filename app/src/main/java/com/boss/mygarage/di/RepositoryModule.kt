package com.boss.mygarage.di

import com.boss.mygarage.data.repository.NoteRepositoryImpl
import com.boss.mygarage.data.repository.VehicleRepositoryImpl
import com.boss.mygarage.domain.repository.NoteRepository
import com.boss.mygarage.domain.repository.VehicleRepository
import org.koin.dsl.module

val repositoryModule = module {
    // single<Interface> { Implementation(dependence) }
    single<VehicleRepository> { VehicleRepositoryImpl(get()) }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
}