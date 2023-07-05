package com.plcoding.notesapp.di

import android.app.Application
import androidx.room.Room
import com.plcoding.notesapp.feature_note.data.data_source.NoteDatabase
import com.plcoding.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.plcoding.notesapp.feature_note.domain.repository.NoteRepository
import com.plcoding.notesapp.feature_note.domain.use_case.AddNote
import com.plcoding.notesapp.feature_note.domain.use_case.DeleteNote
import com.plcoding.notesapp.feature_note.domain.use_case.GetNote
import com.plcoding.notesapp.feature_note.domain.use_case.GetNotes
import com.plcoding.notesapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase { //This method provides an instance of NoteDatabase by using the Room database builder. It takes an Application object as a parameter and returns a NoteDatabase instance.
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository { //This method provides an instance of NoteRepository by taking a NoteDatabase object as a parameter and passing its noteDao (presumably a data access object) to the constructor of NoteRepositoryImpl.
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases { //This method provides an instance of NoteUseCases by taking a NoteRepository object as a parameter.
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}

