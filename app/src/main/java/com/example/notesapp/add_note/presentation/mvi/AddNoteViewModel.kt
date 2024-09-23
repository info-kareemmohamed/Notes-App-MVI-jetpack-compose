package com.example.notesapp.add_note.presentation.mvi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.add_note.domain.use_case.GetNoteById
import com.example.notesapp.add_note.domain.use_case.SearchImages
import com.example.notesapp.add_note.domain.use_case.UpsertNote
import com.example.notesapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val upsertNoteUseCase: UpsertNote,
    private val getNoteByIdUseCase: GetNoteById,
    private val searchImages: SearchImages
) : ViewModel() {
    private val _state = MutableStateFlow(AddNoteState())
    val state = _state.asStateFlow()
    private val _noteSavedChannel = Channel<Boolean>()
    val noteSavedFlow = _noteSavedChannel.receiveAsFlow()

    init {
        savedStateHandle.get<Int>("note_Id")?.takeIf { it != -1 }?.let { id ->
            processIntent(AddNoteIntent.LoadNote(id))
        }

    }


    fun processIntent(intent: AddNoteIntent) {
        when (intent) {

            is AddNoteIntent.LoadNote -> loadNote(intent.id)
            is AddNoteIntent.UpsertNote -> upsertNote(
                intent.title,
                intent.description,
                intent.imageUrl,
                intent.id
            )

            is AddNoteIntent.ChangeDescription -> changeDescription(intent.description)
            is AddNoteIntent.ChangeTitle -> changeTitle(intent.title)
            is AddNoteIntent.ChangeImageUrl -> changeImageUrl(intent.imageUrl)
            is AddNoteIntent.UpdateSearchImageQuery -> {
                _state.update {
                    it.copy(searchImagesQuery = intent.newQuery)
                }
                searchImages(intent.newQuery)
            }

            is AddNoteIntent.UpdateImagesDialogVisibility -> _state.update {
                it.copy(
                    isImagesDialogShowing = !it.isImagesDialogShowing
                )
            }
        }

    }

    private var searchJob: Job? = null

    private fun searchImages(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)

            searchImages.invoke(query)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    imageList = emptyList(),
                                    searchImagesError = result.message,
                                    isLoadingImagesDialog = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(isLoadingImagesDialog = result.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    imageList = result.data?.images ?: emptyList(),
                                    isLoadingImagesDialog = false
                                )
                            }
                        }
                    }
                }
        }
    }


    private fun upsertNote(title: String, description: String, imageUrl: String, id: Int?) {
        viewModelScope.launch {
            _noteSavedChannel.send(upsertNoteUseCase(title, description, imageUrl, id))
        }

    }

    private fun changeImageUrl(imageUrl: String) {
        _state.value = _state.value.copy(imageUrl = imageUrl)
    }

    private fun changeTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    private fun changeDescription(description: String) {
        _state.value = _state.value.copy(
            description = description
        )

    }


    private fun loadNote(id: Int) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val note = getNoteByIdUseCase(id)
                _state.value = _state.value.copy(
                    id = id,
                    title = note?.title ?: "",
                    description = note?.description ?: "",
                    imageUrl = note?.imageUrl ?: "",
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }

    }


}