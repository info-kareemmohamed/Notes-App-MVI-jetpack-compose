package com.example.notesapp.core.presentation.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object NoteList : Route("note_list")
    object NoteDetail : Route("note_detail/{note_Id}", arguments = listOf(
        navArgument("note_Id") {
            type = NavType.IntType
            defaultValue = -1
        }
    ))


}