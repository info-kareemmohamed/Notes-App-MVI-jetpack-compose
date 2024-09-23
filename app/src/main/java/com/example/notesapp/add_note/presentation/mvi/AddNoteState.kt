package com.example.notesapp.add_note.presentation.mvi


data class AddNoteState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val imageList: List<String> = emptyList(),
    val searchImagesQuery: String = "",
    val isLoading: Boolean = false,
    val isLoadingImagesDialog: Boolean = false,
    val error: String? = null,
    val searchImagesError: String? = null,
    val isImagesDialogShowing: Boolean = false


    )