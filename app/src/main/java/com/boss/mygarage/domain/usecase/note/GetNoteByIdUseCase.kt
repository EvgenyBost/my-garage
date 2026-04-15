package com.boss.mygarage.domain.usecase.note

import com.boss.mygarage.domain.model.Note
import com.boss.mygarage.domain.repository.NoteRepository

class GetNoteByIdUseCase (private val repository: NoteRepository) {
    suspend operator fun invoke(noteId: Long): Note? = repository.getNoteById(noteId)
}