package am.mojtaba.armengo.admin.ui.screen.sentence.sheet

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.admin.ui.screen.category.sheet.move
import am.mojtaba.armengo.core.domain.model.Sentence

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortSentenceSheet(
    sentences: List<Sentence>,
    onDismiss: () -> Unit,
    onSubmit: (orderedIds: List<String>) -> Unit // 👈 خروجی لیستی از IDهای جدید به ترتیب است
) {
    // مقداردهی اولیه لیست بدون تغییر ترتیب پیش‌فرضی که از ورودی آمده است
    val list = remember(sentences) {
        mutableStateListOf<Sentence>().apply { addAll(sentences) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            Text("Sort Sentences", style = MaterialTheme.typography.titleLarge)

            Button(
                onClick = {
                    // 👈 استخراج لیست IDها بر اساس مرتب‌سازی فعلی لیست
                    val orderedIds = list.map { it.id }
                    onSubmit(orderedIds)
                }
            ) {
                Text("Save")
            }
        }

        Spacer(Modifier.size(16.dp))

        DraggableLazyColumn(
            items = list,
            key = { item -> item.id }, // 👈 ارسال key برای جلوگیری از مشکلات Recomposition
            onMove = { from, to ->
                list.move(from, to)
            }
        ) { sentence, isDragging ->
            SentenceDragItem(sentence, isDragging)
        }
    }
}

@Composable
fun SentenceDragItem(sentence: Sentence, isDragging: Boolean) {
    val density = LocalDensity.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .graphicsLayer(
                shadowElevation = with(density) { if (isDragging) 16.dp.toPx() else 2.dp.toPx() },
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
                text = sentence.fromText,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// 👈 پشتیبانی از انواع مدل‌ها به صورت Generic
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> DraggableLazyColumn(
    items: SnapshotStateList<T>,
    key: (T) -> Any,
    onMove: (fromIndex: Int, toIndex: Int) -> Unit,
    itemContent: @Composable (T, Boolean) -> Unit
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

                        val pointerY = change.position.y.toInt() + listState.firstVisibleItemScrollOffset

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
        itemsIndexed(items, key = { _, item -> key(item) }) { index, item ->
            Box(
                modifier = Modifier.animateItem(
                    fadeInSpec = null,
                    fadeOutSpec = null,
                    placementSpec = spring(stiffness = Spring.StiffnessMediumLow)
                )
            ) {
                itemContent(item, draggingItemIndex == index)
            }
        }
    }

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