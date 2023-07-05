package com.plcoding.notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.notesapp.ui.theme.*

@Entity //This annotation is typically used in Android Room database framework to mark a class as an entity, representing a table in the database.
data class Note(   //This class represents a note entity in the application.
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message) //It is used to indicate that a note is invalid based on some criteria.