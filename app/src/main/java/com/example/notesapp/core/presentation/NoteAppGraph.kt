package com.example.notesapp.core.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesapp.add_note.presentation.AddNoteScreen
import com.example.notesapp.core.presentation.ui.Route
import com.example.notesapp.note_list.presentation.NoteListScreen


@Composable
fun NoteAppGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.NoteList.route) {
        composable(Route.NoteList.route) {
            NoteListScreen(
                onNavigateToAddNote = { id ->
                    navController.navigate("note_detail/$id")
                }
            )
        }

        composable(
            Route.NoteDetail.route,
            arguments = Route.NoteDetail.arguments
        ) {

            AddNoteScreen(
                onNavigateToListNote = {
                    navController.popBackStack()
                }
            )
        }

    }
}