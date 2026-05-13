package com.appricut.easylezo.ui.screen.sentence.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.ui.component.LanguageAwareText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSentenceSheet(
    sentence: Sentence?,
    onDismiss: () -> Unit,
    onPlay: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            LanguageAwareText(
                text = sentence?.toText ?: "",
                fontSize = 32.sp,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            LanguageAwareText(
                text = sentence?.fromText ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(52.dp))

            IconButton (
                modifier = Modifier
                    .size(64.dp,70.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(20.dp)),
                onClick = {
//                    currentSentence = sentence
//                    showSentenceSheet = true
                }) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Play"
                )
            }

            Spacer(Modifier.height(96.dp))

        }
    }
}
