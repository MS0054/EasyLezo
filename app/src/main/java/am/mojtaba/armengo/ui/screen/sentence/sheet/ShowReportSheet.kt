package am.mojtaba.armengo.ui.screen.sentence.sheet

import am.mojtaba.armengo.core.domain.model.ReportMessage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowReportSheet(
    reportMessages: List<ReportMessage>,
    onDismiss: () -> Unit,
    onSubmit: (reportMessageKey: String, userComment: String) -> Unit,
    onCancel: () -> Unit = onDismiss
) {
    var problemText by remember { mutableStateOf("") }
    var selectedReportMessageKey by remember { mutableStateOf<String?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            // 1. Header
            Text(
                text = "Report Problem",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 2. List of ReportMessages
            if (reportMessages.isNotEmpty()) {
                Text(
                    text = "Select a reason:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(reportMessages) { message ->
                        val isSelected = selectedReportMessageKey == message.key
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedReportMessageKey = if (isSelected) null else message.key
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp,0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = message.text.ifEmpty { message.key },
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    if (message.description.isNotEmpty()) {
                                        Text(
                                            text = message.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        selectedReportMessageKey = if (isSelected) null else message.key
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // 3. Input box for writing the problem
            OutlinedTextField(
                value = problemText,
                onValueChange = { problemText = it },
                label = { Text("Write the problem...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Two buttons: confirmation and cancel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        onSubmit(selectedReportMessageKey ?: "", problemText)
                    }
                ) {
                    Text("Confirm")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
