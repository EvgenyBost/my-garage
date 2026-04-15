package com.boss.mygarage.domain.usecase.note

import com.boss.mygarage.domain.model.Note
import com.boss.mygarage.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesForVehicleUseCase(private val repository: NoteRepository) {
    operator fun invoke(vehicleId: Long): Flow<List<Note>> = repository.getNotesForVehicle(vehicleId)
}