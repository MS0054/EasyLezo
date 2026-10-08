package am.mojtaba.armengo.admin.ui.screen.report

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.core.domain.model.Report

import androidx.compose.foundation.clickable

fun parseHexColor(colorHex: String): Color {
    return try {
        val hex = colorHex.trim().removePrefix("#")
        if (hex.length == 6 || hex.length == 8) {
            Color(android.graphics.Color.parseColor("#$hex"))
        } else {
            Color.Gray
        }
    } catch (e: Exception) {
        Color.Gray
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReportS(
    reportV: ReportV,
    onShow: (Report) -> Unit,
    onEdit: (Report) -> Unit,
    onChangeStatus: (Report) -> Unit,
    onAdd: () -> Unit
) {
    val reportsUiState by reportV.reportsUiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Report"
                )
            }
        }
    ) { paddingValues ->
        when {
            reportsUiState.isLoading -> CircularProgressIndicator()
            reportsUiState.error != null -> {
                Text("Error: ${reportsUiState.error}", color = MaterialTheme.colorScheme.error)
            }

            else -> {
                val reports = reportsUiState.data ?: emptyList()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(reports) { report ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .combinedClickable(
                                        onClick = {
                                            onShow(report)
                                        },
                                        onLongClick = {
                                            onEdit(report)
                                        }
                                    )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    val statusColor = parseHexColor(report.reportStatusType.color)
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .size(24.dp)
                                            .clickable {
                                                onChangeStatus(report)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .background(statusColor, shape = CircleShape)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 28.dp)
                                    ) {
                                        Text(
                                            text = "${report.reportMessageKey} | ${report.reportType}",
                                            fontSize = 16.sp,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        if (report.userComment.isNotBlank()) {
                                            Text(
                                                text = " ${report.userComment}",
                                                fontSize = 14.sp,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                        Text(
                                            text = "Device: ${report.deviceInfo} | App Ver: ${report.appVersion} | Lang: ${report.userAppLanguage}",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.outline
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
