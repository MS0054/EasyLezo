package am.mojtaba.armengo.ui.screen.sentence

import am.mojtaba.armengo.app.R
import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.ui.component.LanguageAwareText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun WordsList(
    words: List<Word>,
    onWordClick: (Word) -> Unit,
    onPlayVoice: (String) -> Unit
) {
    if (words.isEmpty()) return

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LanguageAwareText(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            text = "W o r d s",
            color = Color.DarkGray,
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineLarge
        )



        LazyRow(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = words,
                key = { it.id }
            ) { word ->
                WordItem(
                    word = word,
                    openSheet = {
                        onWordClick(word)
                    },
                    playVoice = {
                        onPlayVoice(word.voiceUrl)
                    }
                )
            }
        }
    }
}


@Composable
fun WordItem(word: Word, openSheet: () -> Unit, playVoice: () -> Unit) {
    val actionIcon = if (word.hasVoice) Icons.Rounded.PlayArrow else Icons.Rounded.ArrowForward
    val actionClick = if (word.hasVoice) playVoice else openSheet
    Card(
        shape = RoundedCornerShape(20.dp),
//        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        onClick = openSheet
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (word.image.isNotEmpty()) {
                AsyncImage(
                    model = word.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Crop
                )
            }
            LanguageAwareText(
                modifier = Modifier.fillMaxWidth(),
                text = word.fromText,
                fontSize = 18.sp
            )
            LanguageAwareText(
                modifier = Modifier.fillMaxWidth(),
                text = word.toText,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(12.dp))

//            if (word.hasVoice) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_volume), // Replace with your actual filename
//                    contentDescription = "Descriptive text",
//                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
//                    modifier = Modifier.size(28.dp) // Set your desired icon size
//                )
//            }
        }




//        IconButton(
//            modifier = Modifier
//                .size(64.dp),
//            onClick = actionClick
//        ) {
//            Icon(actionIcon, contentDescription = "Play", Modifier.size(28.dp))
//        }

    }

}
