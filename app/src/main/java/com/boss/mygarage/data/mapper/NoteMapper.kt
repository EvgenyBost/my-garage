package com.boss.mygarage.data.mapper

import com.boss.mygarage.data.local.entities.NoteEntity
import com.boss.mygarage.domain.model.Note

fun NoteEntity.toDomain(): Note {
    return Note(
        id = this.noteId,
        vehicleId = this.vehicleId,
        content = this.content,
        timestamp = this.timestamp,
        name = this.name
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        noteId = this.id,
        vehicleId = this.vehicleId,
        content = this.content,
        timestamp = this.timestamp,
        name = this.name
    )
}