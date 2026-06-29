package am.mojtaba.armengo.admin.ui.screen.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.admin.ui.component.LanguageAwareText
import am.mojtaba.armengo.core.domain.model.Category
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryS(
    categoryV: CategoryV,
    onEdit: (Category) -> Unit,
    onAdd: (Int) -> Unit,
    openSentences: (String) -> Unit,
    openWords: (String) -> Unit
) {
    val categoriesUiState by categoryV.categoryUiState.collectAsStateWithLifecycle()

    // ۲. محاسبه‌ی هوشمند و بهینه‌ی maxOrder با استفاده از derivedStateOf برای جلوگیری از Re-composition تکراری
    val maxOrder by remember {
        derivedStateOf {
            val categories = categoriesUiState.data ?: emptyList()
            // پیدا کردن بزرگترین order موجود و اضافه کردن ۱ واحد به آن، در غیر این صورت صفر
            (categories.maxOfOrNull { it.order } ?: -1) + 1
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd(maxOrder) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Category"
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                categoriesUiState.isLoading -> {
                    CircularProgressIndicator()
                }

                categoriesUiState.error != null -> {
                    Text(
                        text = "Error: ${categoriesUiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    val categories = categoriesUiState.data ?: emptyList()

                    if (categories.isEmpty()) {
                        Text(
                            text = "No categories available.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            // ۳. معرفی key ثابت (id) برای افزایش فوق‌العاده سرعت رندر و اسکرول لیست
                            items(items = categories) { cat ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .combinedClickable(
                                            onClick = { },
                                            onLongClick = { onEdit(cat) }
                                        )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        LanguageAwareText(
                                            modifier = Modifier.fillMaxWidth().weight(1f),
                                            text = cat.fromText,
                                            fontSize = 16.sp,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Spacer(Modifier.size(16.dp))
                                        Button(
                                            modifier = Modifier.size(60.dp),
                                            onClick = {
                                                openSentences(cat.id)
                                            }) {
                                            Text("S")
                                        }
                                        Spacer(Modifier.size(16.dp))
                                        Button(
                                            modifier = Modifier.size(60.dp),
                                            onClick = {
                                            openWords(cat.id)
                                        }) {
                                            Text("W")
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