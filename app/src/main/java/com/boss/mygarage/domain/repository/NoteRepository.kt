package com.boss.mygarage.domain.repository

import com.boss.mygarage.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotesForVehicle(vehicleId: Long): Flow<List<Note>>
    suspend fun getNoteById(noteId: Long): Note?
    suspend fun saveNote(note: Note)
    suspend fun deleteNote(note: Note)
}