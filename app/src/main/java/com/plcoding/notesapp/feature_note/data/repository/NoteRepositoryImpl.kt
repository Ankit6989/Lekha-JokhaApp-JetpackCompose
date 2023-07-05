package com.plcoding.notesapp.feature_note.data.repository

import com.plcoding.notesapp.feature_note.data.data_source.NoteDao
import com.plcoding.notesapp.feature_note.domain.model.Note
import com.plcoding.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(   //This is the note Repo where basic operations data is stored and implemented

    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}