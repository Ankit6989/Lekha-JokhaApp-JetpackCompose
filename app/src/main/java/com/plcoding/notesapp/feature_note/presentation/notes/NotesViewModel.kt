package com.plcoding.notesapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.notesapp.feature_note.domain.model.Note
import com.plcoding.notesapp.feature_note.domain.use_case.NoteUseCases
import com.plcoding.notesapp.feature_note.domain.util.NoteOrder
import com.plcoding.notesapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {   //This function handles different events related to notes, such as ordering, deleting, restoring notes, or toggling the visibility of the order section.
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)  //It fetches the notes based on the specified order and updates the UI state accordingly.
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    //The coroutines perform asynchronous operations such as deleting notes or adding/restoring recently deleted notes using the noteUseCases provided through dependency injection.
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {  //The coroutines perform asynchronous operations such as deleting notes or adding/restoring recently deleted notes using the noteUseCases provided through dependency injection.
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(  //These lines update the value of the _state mutable state by creating a new instance of NotesState with modified properties.
                    //It uses the copy function to create a copy of the existing state and modify specific properties.

                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {  //This private function is responsible for fetching the notes based on the provided noteOrder.
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}
