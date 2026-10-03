package am.mojtaba.armengo.admin.ui.screen.reportMessage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.core.domain.model.ReportMessage

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReportMessageS(
    reportMessageV: ReportMessageV,
    onEdit: (ReportMessage) -> Unit,
    onAdd: () -> Unit
) {
    val reportMessagesUiState by reportMessageV.reportMessagesUiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add ReportMessage"
                )
            }
        }
    ) { paddingValues ->
        when {
            reportMessagesUiState.isLoading -> CircularProgressIndicator()
            reportMessagesUiState.error != null -> {
                Text("Error: ${reportMessagesUiState.error}", color = MaterialTheme.colorScheme.error)
            }

            else -> {
                val reportMessages = reportMessagesUiState.data ?: emptyList()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(reportMessages) { reportMessage ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .combinedClickable(
                                        onClick = {},
                                        onLongClick = {
                                            onEdit(reportMessage)
                                        }
                                    )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        reportMessage.key,
                                        fontSize = 16.sp,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    if (reportMessage.text.isNotBlank()) {
                                        Text(
                                            reportMessage.text,
                                            fontSize = 14.sp,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                    if (reportMessage.reportTypes.isNotEmpty()) {
                                        Spacer(Modifier.height(4.dp))
                                        Row {
                                            reportMessage.reportTypes.forEach { typeKey ->
                                                AssistChip(
                                                    onClick = {},
                                                    label = { Text(typeKey, fontSize = 12.sp) },
                                                    modifier = Modifier.padding(end = 4.dp)
                                                )
                                            }
                                        }
                                    }
                                    if (reportMessage.description.isNotBlank()) {
                                        Text(
                                            reportMessage.description,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
