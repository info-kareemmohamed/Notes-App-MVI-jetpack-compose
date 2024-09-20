package com.example.notesapp.note_list.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notesapp.core.presentation.component.ErrorScreen
import com.example.notesapp.core.presentation.component.LoadingScreen
import com.example.notesapp.note_list.presentation.component.ListNoteItem
import com.example.notesapp.note_list.presentation.component.TopBar
import com.example.notesapp.note_list.presentation.mvi.NoteListIntent
import com.example.notesapp.note_list.presentation.mvi.NoteListViewModel


@Composable
fun NoteListScreen(
    onNavigateToAddNote: (id: Int?) -> Unit,
    viewModel: NoteListViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.processIntent(NoteListIntent.SortNotesByTitle(state.sortOrderByTitle))
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(notesCount = state.notes.size, onSortClick = {
                viewModel.processIntent(NoteListIntent.SortNotesByTitle(!state.sortOrderByTitle))
            }, sort = state.sortOrderByTitle)
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = { onNavigateToAddNote(-1) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }

    ) { paddingValues ->

        if (state.error != null) {
            ErrorScreen(errorMessage = state.error.toString())
        } else if (state.isLoading) {
            LoadingScreen()
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding()),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {

                items(state.notes) { note ->
                    ListNoteItem(noteItem = note, onClick = { onNavigateToAddNote(it) }) {
                        viewModel.processIntent(NoteListIntent.DeleteNote(note))
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }


            }


        }
    }

}