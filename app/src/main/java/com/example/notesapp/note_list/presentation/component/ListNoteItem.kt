package com.example.notesapp.note_list.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.notesapp.core.domain.model.Note
import com.example.notesapp.core.presentation.ui.theme.NotesAppTheme

@Composable
fun ListNoteItem(
    noteItem: Note,
    modifier: Modifier = Modifier,
    onClick: (id: Int?) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .padding(8.dp),
        onClick = { onClick(noteItem.id) },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(5.dp)
    )

    {


        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondary),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(noteItem.imageUrl)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = noteItem.title,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {

                Text(
                    text = noteItem.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = noteItem.description,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }

            Icon(
                modifier = Modifier
                    .clickable { onDelete() },
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = Color.Black,
            )




        }



    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListNoteItemPreview() {
    NotesAppTheme {
        ListNoteItem(
            onDelete = {}, onClick = {}, noteItem = Note(
                title = "Hi",
                id = 1,
                dateAdded = 5,
                description = "Kareem",
                imageUrl = "sdad"
            )
        )
    }
}
