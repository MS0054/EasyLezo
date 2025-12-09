// SentenceListScreen.kt
package com.appricut.easylezo.ui.screen.sentence

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import coil3.compose.AsyncImage
import com.appricut.easylezo.ui.screen.main.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SentenceListScreen(
    categoryId: String,
    viewModel: MainViewModel = hiltViewModel(),
    isAdmin: Boolean = false
) {
    val sentences by viewModel.sentences.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val pagerState = rememberPagerState(pageCount = { sentences.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(categoryId) {
        viewModel.selectCategory(categoryId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Sentences", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))

        if (loading) CircularProgressIndicator()
        error?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }

        if (sentences.isEmpty() && !loading) {
            Text("No sentences", style = MaterialTheme.typography.bodyLarge)
        }

        if (sentences.isNotEmpty()) {
            // Horizontal pager view (slide)
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                val s = sentences[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        if (s.image.isNotEmpty()) {
                            AsyncImage(
                                model = s.image,
                                contentDescription = s.ar,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                        }

                        Text(text = s.ar, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                        Text(text = s.fa, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
                        Text(text = "Level: ${s.level}", style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(8.dp))

                        if (isAdmin) {
                            Row {
                                IconButton(onClick = {
                                    // delete
                                    viewModel.deleteSentence(categoryId, s.id) { ok, msg ->
                                        // optionally show toast / snackbar
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                                Spacer(Modifier.width(8.dp))
                                Button(onClick = {
                                    // go to edit flow — not implemented here
                                }) {
                                    Text("Edit")
                                }
                            }
                        }
                    }
                }
            }

            // pager indicators + quick jump
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    coroutineScope.launch {
                        val new = (pagerState.currentPage - 1).coerceAtLeast(0)
                        pagerState.animateScrollToPage(new)
                    }
                }) { Text("Prev") }
                Spacer(Modifier.width(12.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        val new = (pagerState.currentPage + 1).coerceAtMost(sentences.lastIndex)
                        pagerState.animateScrollToPage(new)
                    }
                }) { Text("Next") }
            }
        }
    }
}
