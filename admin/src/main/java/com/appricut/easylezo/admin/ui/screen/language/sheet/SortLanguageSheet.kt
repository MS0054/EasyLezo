package com.appricut.easylezo.admin.ui.screen.language.sheet

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.admin.ui.screen.category.sheet.move
import com.appricut.easylezo.core.domain.model.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortLanguageSheet(
    languages: List<Language>,
    onDismiss: () -> Unit,
    onSubmit: (List<Language>) -> Unit
) {
    val list = remember { mutableStateListOf<Language>().apply { addAll(languages.sortedBy { it.order }) } }

        Column(Modifier.Companion.fillMaxWidth().padding(20.dp)) {
            Row(Modifier.Companion.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
                Text("Sort Languages", style = MaterialTheme.typography.titleLarge)
                Button(onClick = { val reordered = list.mapIndexed { index, cat -> cat.copy(order = index) }; onSubmit(reordered) }) { Text("Save") }
            }
            Spacer(Modifier.Companion.size(16.dp))

            DraggableLazyColumn(
                items = list,
                onMove = { from, to ->
                    list.move(from, to)
                }
            ) { language, isDragging ->
                LanguageDragItem(language, isDragging)
            }

        }
    }

@Composable
fun LanguageDragItem(language: Language, isDragging: Boolean) {
    val density = LocalDensity.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .graphicsLayer(
                shadowElevation = with(density) {if (isDragging) 16.dp.toPx() else 2.dp.toPx()},
                scaleX = if (isDragging) 1.03f else 1f,
                scaleY = if (isDragging) 1.03f else 1f
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Drag",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(12.dp))
            Text(
                language.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableLazyColumn(
    items: SnapshotStateList<Language>,
    onMove: (fromIndex: Int, toIndex: Int) -> Unit,
    itemContent: @Composable (Language, Boolean) -> Unit
) {
    val listState = rememberLazyListState()
    var draggingItemIndex by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(items) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset ->
                        draggingItemIndex = listState.layoutInfo.visibleItemsInfo
                            .firstOrNull { offset.y.toInt() in it.offset..(it.offset + it.size) }
                            ?.index
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        val currentIndex = draggingItemIndex ?: return@detectDragGesturesAfterLongPress

                        // مختصات pointer نسبت به کل لیست
                        val pointerY = change.position.y.toInt() + listState.firstVisibleItemScrollOffset

                        // پیدا کردن آیتمی که pointer روی اون قرار گرفته
                        val newIndex = listState.layoutInfo.visibleItemsInfo
                            .firstOrNull { pointerY in it.offset..(it.offset + it.size) }
                            ?.index

                        if (newIndex != null && newIndex != currentIndex) {
                            onMove(currentIndex, newIndex)
                            draggingItemIndex = newIndex
                        }
                    },
                    onDragEnd = { draggingItemIndex = null },
                    onDragCancel = { draggingItemIndex = null }
                )
            }
    ) {
        itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
            Box(
                modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null, placementSpec = spring(stiffness = Spring.StiffnessMediumLow)
                )
            ) {
                itemContent(item, draggingItemIndex == index)
            }
        }
    }

    // Auto-scroll
    LaunchedEffect(draggingItemIndex) {
        val index = draggingItemIndex ?: return@LaunchedEffect
        val visibleItemInfo = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
        visibleItemInfo?.let {
            val threshold = 100
            val speed = 20f
            val listHeight = listState.layoutInfo.viewportEndOffset

            if (it.offset + it.size > listHeight - threshold) {
                listState.scrollBy(speed)
            } else if (it.offset < threshold) {
                listState.scrollBy(-speed)
            }
        }
    }
}