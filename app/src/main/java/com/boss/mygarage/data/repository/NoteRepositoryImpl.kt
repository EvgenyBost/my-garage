package com.boss.mygarage.data.repository

import com.boss.mygarage.data.local.dao.NoteDao
import com.boss.mygarage.data.mapper.toDomain
import com.boss.mygarage.data.mapper.toEntity
import com.boss.mygarage.domain.model.Note
import com.boss.mygarage.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {
    override fun getNotesForVehicle(vehicleId: Long): Flow<List<Note>> {
        return noteDao.getNotesForVehicle(vehicleId).map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getNoteById(noteId: Long): Note? {
        return noteDao.getNoteById(noteId)?.toDomain()
    }

    override suspend fun saveNote(note: Note) {
        noteDao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }
}