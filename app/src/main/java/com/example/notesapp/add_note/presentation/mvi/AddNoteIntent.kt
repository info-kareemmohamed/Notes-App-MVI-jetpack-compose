package com.example.notesapp.add_note.presentation.mvi

sealed class AddNoteIntent {
    data class LoadNote(val id: Int) : AddNoteIntent()
    data class UpsertNote(val title: String, val description: String, val imageUrl: String,val id: Int?) :AddNoteIntent()
    data class ChangeTitle(val title:String) : AddNoteIntent()
    data class ChangeDescription(val description:String) : AddNoteIntent()
    data class ChangeImageUrl(val imageUrl:String) : AddNoteIntent()
    data class UpdateSearchImageQuery(val newQuery: String) : AddNoteIntent()
    data object UpdateImagesDialogVisibility : AddNoteIntent()



}