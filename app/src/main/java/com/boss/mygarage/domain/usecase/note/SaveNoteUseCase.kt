package com.boss.mygarage.domain.usecase.note

import com.boss.mygarage.domain.model.Note
import com.boss.mygarage.domain.repository.NoteRepository

class SaveNoteUseCase (private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.saveNote(note)
}