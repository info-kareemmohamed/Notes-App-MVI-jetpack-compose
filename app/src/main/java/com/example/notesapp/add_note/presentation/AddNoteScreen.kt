package com.example.notesapp.add_note.presentation

import android.widget.Toast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.notesapp.add_note.presentation.mvi.AddNoteIntent
import com.example.notesapp.add_note.presentation.mvi.AddNoteState
import com.example.notesapp.add_note.presentation.mvi.AddNoteViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel = hiltViewModel(),
    onNavigateToListNote: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.noteSavedFlow.collectLatest { saved ->
            if (saved) {
                onNavigateToListNote()
            } else {
                Toast.makeText(
                    context,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Card(onClick = { /*TODO*/ }) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { viewModel.processIntent(AddNoteIntent.UpdateImagesDialogVisibility) }
                        .background(MaterialTheme.colorScheme.secondary),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.imageUrl)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = { viewModel.processIntent(AddNoteIntent.ChangeTitle(it)) },
                label = { Text(text = "Title") }
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(100.dp),
                value = state.description,
                onValueChange = { viewModel.processIntent(AddNoteIntent.ChangeDescription(it)) },
                label = { Text(text = "Description") }
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                viewModel.processIntent(
                    AddNoteIntent.UpsertNote(
                        state.title,
                        state.description,
                        state.imageUrl,
                        state.id
                    )
                )
            }) {
                Text(text = "Save")
            }

        }

        if (state.isImagesDialogShowing) {

            Dialog(
                onDismissRequest = {
                    viewModel.processIntent(AddNoteIntent.UpdateImagesDialogVisibility)
                }
            ) {
                ImageDialog(addNoteState = state, onImageClick = {
                    viewModel.processIntent(AddNoteIntent.ChangeImageUrl(it))
                }) { searchQuery ->
                    viewModel.processIntent(AddNoteIntent.UpdateSearchImageQuery(searchQuery))
                }
            }

        }
    }

}


@Composable
fun ImageDialog(
    addNoteState: AddNoteState,
    onImageClick: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit


) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = addNoteState.searchImagesQuery,
            onValueChange = { onSearchQueryChange(it) },
            label = { Text(text = "Search") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (addNoteState.isLoadingImagesDialog) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(125.dp),
                contentPadding = PaddingValues(10.dp)
            ) {

                items(addNoteState.imageList) { url ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                            .clickable { onImageClick(url) },
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(url)
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )


                }


            }

        }


    }


}


