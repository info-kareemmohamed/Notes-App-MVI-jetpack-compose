package com.example.notesapp.note_list.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notesapp.note_list.presentation.component.ListNoteItem
import com.example.notesapp.note_list.presentation.component.TopBar
import com.example.notesapp.note_list.presentation.mvi.NoteListIntent
import com.example.notesapp.note_list.presentation.mvi.NoteListViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),

    ) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopBar(notesCount = 10, onSortClick = {}, sort = "D")
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {

            items(state.notes) { note ->
                ListNoteItem(noteItem = note) {
                    viewModel.processIntent(NoteListIntent.DeleteNote(note))
                }
                Spacer(modifier = Modifier.height(10.dp))

            }


        }
    }


}