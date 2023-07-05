package com.plcoding.notesapp.feature_note.domain.repository

import com.plcoding.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {  //This is the Note repository

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}