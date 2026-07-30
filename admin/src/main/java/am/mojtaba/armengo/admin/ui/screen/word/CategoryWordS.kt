package am.mojtaba.armengo.admin.ui.screen.word

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.admin.ui.component.LanguageAwareText
import am.mojtaba.armengo.core.domain.model.Word
import coil3.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CategoryWordS(
    categoryId: String,
    categoryWordV: CategoryWordV,
    onAdd: (Int) -> Unit,
    onEdit: (Word) -> Unit,
) {
    val wordUiState by categoryWordV.categoryWordsUiState.collectAsState()
    var maxOrder by remember { mutableIntStateOf(0) }

    LaunchedEffect(categoryId) {
        categoryWordV.observeCategoryWords(categoryId)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd(maxOrder) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Word"
                )
            }
        }
    ) { paddingValues ->
        when {
            wordUiState.isLoading -> CircularProgressIndicator()
            wordUiState.error != null -> {
                Text("Error: ${wordUiState.error}", color = MaterialTheme.colorScheme.error)
            }
            else -> {
                val words = wordUiState.data ?: emptyList()
                maxOrder = words.size
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (words.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            itemsIndexed(words) { index, word ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                        .combinedClickable(
                                            onClick = { /* Handle click */ },
                                            onLongClick = { onEdit(word) }
                                        )
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                                        AsyncImage(
                                            model = word.image,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(60.dp)
                                                .padding(8.dp),
                                            contentScale = ContentScale.Fit
                                        )
                                        Column(modifier = Modifier.padding(12.dp, 10.dp)) {
                                            LanguageAwareText(
                                                word.fromText,
                                                fontSize = 16.sp,
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            LanguageAwareText(
                                                word.toText,
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
}
