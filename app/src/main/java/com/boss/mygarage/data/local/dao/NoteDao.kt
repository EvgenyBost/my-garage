package com.boss.mygarage.data.local.dao

import androidx.room.*
import com.boss.mygarage.data.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // Use Flow, to update UI automatically
    @Query("SELECT * FROM notes WHERE vehicleId = :vehicleId ORDER BY timestamp DESC")
    fun getNotesForVehicle(vehicleId: Long): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    suspend fun getNoteById(noteId: Long): NoteEntity?
}