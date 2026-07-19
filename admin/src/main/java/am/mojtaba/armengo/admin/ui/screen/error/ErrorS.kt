package am.mojtaba.armengo.admin.ui.screen.error

import am.mojtaba.armengo.admin.ui.component.LanguageAwareText
import am.mojtaba.armengo.core.domain.model.Error
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ErrorS(
    errorV: ErrorV,
    onAdd: () -> Unit,
    onEdit: (Error) -> Unit,
) {
    val errorUiState by errorV.errorUiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Error"
                )
            }
        }
    ) { paddingValues ->
        when {
            errorUiState.isLoading -> CircularProgressIndicator()
            errorUiState.error != null -> {
                Text("Error: ${errorUiState.error}", color = MaterialTheme.colorScheme.error)
            }

            else -> {
                val words = errorUiState.data ?: emptyList()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (words.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            itemsIndexed(words) { index, error ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                        .combinedClickable(
                                            onClick = {
                                            },
                                            onLongClick = {
                                                onEdit(error)
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
                                                error.code,
                                                fontSize = 16.sp,
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