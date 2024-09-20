package com.example.notesapp.note_list.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.core.domain.model.Note
import com.example.notesapp.note_list.domain.use_case.DeleteNote
import com.example.notesapp.note_list.domain.use_case.GetNotesSortedByDateAdded
import com.example.notesapp.note_list.domain.use_case.GetNotesSortedByTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNote,
    private val getNotesSortedByTitle: GetNotesSortedByTitle,
    private val getNotesSortedByDate: GetNotesSortedByDateAdded,
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state = _state.asStateFlow()



    fun processIntent(intent: NoteListIntent) {
        when (intent) {
            is NoteListIntent.SortNotesByTitle -> {
                if (intent.sort)
                    loadNotes { getNotesSortedByTitle() }
                else
                    loadNotes { getNotesSortedByDate() }

                _state.update {
                    it.copy(
                        sortOrderByTitle = intent.sort
                    )
                }
            }

            is NoteListIntent.DeleteNote -> deleteNote(intent.note)

        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteNoteUseCase(note)
                 processIntent(NoteListIntent.SortNotesByTitle(state.value.sortOrderByTitle))
            } catch (e: Exception) {
                updateStateWithError(e)
            }
        }
    }

    private fun loadNotes(getNotes: suspend () -> List<Note>) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val notes = getNotes()
                _state.value = _state.value.copy(
                    notes = notes,
                    isLoading = false
                )
            } catch (e: Exception) {
                updateStateWithError(e)
            }
        }
    }

    private fun updateStateWithError(e: Exception) {
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }
}