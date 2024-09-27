package com.example.notesapp.note_list.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.core.util.TestTags.NOTES_COUNT_TEXT

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    notesCount: Int,
    onSortClick: () -> Unit,
    sort: Boolean

) {



    Row(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

    ) {

        Text(text = "$notesCount Notes",fontSize = 19.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.testTag(NOTES_COUNT_TEXT+notesCount))

        Row (
            modifier = Modifier.clickable { onSortClick() },
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(text = if(sort) "T" else "D", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(imageVector = Icons.AutoMirrored.Filled.Sort, contentDescription = null)

        }


    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopBarPreview() {
    TopBar(notesCount = 10, onSortClick = {}, sort = false)
}
