package com.appricut.easylezo.admin.ui.screen.sentence

import android.os.Environment
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.appricut.easylezo.admin.ui.component.LanguageAwareText
import com.appricut.easylezo.core.domain.model.Sentence
import java.io.File


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SentenceS(
    categoryId: String,
    sentenceV: SentenceV,
    onAdd: (Int) -> Unit,
    onEdit: (Sentence) -> Unit,
) {
    val mContext = LocalContext.current
    var runPlayer by remember { mutableStateOf(false) }
    val sentenceUiState by sentenceV.sentenceUiState.collectAsState()
    var maxOrder by remember { mutableIntStateOf(0) }


    LaunchedEffect(categoryId) {
        sentenceV.getSentences(categoryId)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd(maxOrder) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Language"
                )
            }
        }
    ) { paddingValues ->
        when {
            sentenceUiState.isLoading -> CircularProgressIndicator()
            sentenceUiState.error != null -> {
                Text("Error: ${sentenceUiState.error}", color = MaterialTheme.colorScheme.error)
            }

            else -> {
                val sentences = sentenceUiState.data ?: emptyList()
                maxOrder = sentences.size
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (sentences.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            itemsIndexed(sentences) { index, sentence ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                        .combinedClickable(
                                            onClick = {
                                                runPlayer = true
                                            },
                                            onLongClick = {
                                                onEdit(sentence)
                                            }
                                        )
                                ) {
                                    Row {
                                        LanguageAwareText(
                                            index.toString(),
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .background(
                                                    Color.LightGray, shape = CircleShape
                                                )
                                                .padding(12.dp, 24.dp),
                                            fontSize = 20.sp,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Column(modifier = Modifier.padding(12.dp, 10.dp)) {
                                            LanguageAwareText(
                                                sentence.fromText,
                                                fontSize = 16.sp,
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            LanguageAwareText(
                                                sentence.toText,
                                                fontSize = 13.sp,
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (runPlayer) {
//        val myFile = File(mContext.filesDir.toString() + "/word/" + "" + ".mp3")
        val myFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/word/" + "5616132941561" + ".mp3")
//        val saveDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "word")



        val exoPlayer = remember { ExoPlayer.Builder(mContext).build() }
        val mediaSource = remember(myFile.absolutePath) { MediaItem.fromUri(myFile.absolutePath) }
//        val mediaSource = remember(myUri) { MediaItem.fromUri(myUri) }
        LaunchedEffect(mediaSource) {
            exoPlayer.setMediaItem(mediaSource);
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            runPlayer = false
        }
//        DisposableEffect(Unit) { onDispose { exoPlayer.release() ;runPlayer =false } }
    }
}