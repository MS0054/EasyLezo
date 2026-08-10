package am.mojtaba.armengo.ui.screen.sentence

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.ui.component.LanguageAwareText
import am.mojtaba.armengo.ui.screen.category.CategoryUiState
import am.mojtaba.armengo.ui.screen.sentence.sheet.ShowSentenceSheet
import android.graphics.drawable.GradientDrawable
import android.util.Log
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.ui.graphics.graphicsLayer


@Composable
fun SentenceScreen(
    uiState: SentenceUiState,
    onSentenceClick: (Sentence) -> Unit,
    onWordClick: (Word) -> Unit,
    onPlayVoice: (String) -> Unit,
    onBack: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 42.dp) ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            IconButton(
                onClick = onBack
            ) {
                Icon(Icons.Default.ArrowBack, null)
            }

            LanguageAwareText(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                textAlign = TextAlign.Center,
                text = uiState.title,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        when {
            uiState.isLoading -> {
                SentenceShimmerList()
            }

            else -> {
                WordsList(uiState.words, onWordClick, onPlayVoice)
                SentencesList(uiState.sentences, onSentenceClick, onPlayVoice)
            }
        }
    }
}

@Composable
fun SentencesList(
    sentences: List<Sentence>,
    onSentenceClick: (Sentence) -> Unit,
    onPlayVoice: (String) -> Unit,
) {
    val listState = rememberLazyListState()

    // ۲. محاسبه پویای شفافیت (Alpha) بر اساس اسکرول اولین آیتم
    val titleAlpha by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex > 0) {
                // اگر کاربر اسکرول کرده و آیتم‌های بعدی نمایان شده‌اند، تایتل کاملا محو شود
                0f
            } else {
                // محاسبه نسبت اسکرول آیتم اول؛ هرچه بیشتر اسکرول شود، آلفا کمتر می‌شود
                val firstItemSize = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 1
                val scrollOffset = listState.firstVisibleItemScrollOffset
                // عدد 0.5f در مخرج برای این است که انیمیشن محو شدن سریع‌تر و نرم‌تر اتفاق بیفتد
                val progress = (scrollOffset.toFloat() / (firstItemSize * 0.5f))
                (1f - progress).coerceIn(0f, 1f)
            }
        }
    }

    Box {
        LazyColumn(
            state = listState, // ۳. اختصاص دادن state به لیست
            contentPadding = PaddingValues(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 360.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = sentences,
                key = { it.id }
            ) { sentence ->
                SentenceItem(
                    sentence = sentence,
                    openSheet = {
                        onSentenceClick(sentence)
                    },
                    playVoice = {
                        onPlayVoice(sentence.voiceUrl)
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            Color.Transparent
                        )
                    )
                )
        ) {
            Spacer(Modifier.height(16.dp))
            LanguageAwareText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .graphicsLayer {
                        alpha = titleAlpha // اعمال آلفا برای محو شدن نرم
                        // اختیاری: می‌توانید کمی جابجایی در محور Y (ترجمه حرکتی) هم اضافه کنید تا افکت زیباتر شود
                        translationY = -1f * (1f - titleAlpha) * 25.dp.toPx()
                    },
                textAlign = TextAlign.Start,
                text = "S e n t e n c e s",
                color = Color.DarkGray,
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineLarge
            )
//            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
fun SentenceItem(sentence: Sentence, openSheet: () -> Unit, playVoice: () -> Unit) {
    val actionIcon =
        if (sentence.hasVoice) Icons.Rounded.PlayArrow else Icons.Rounded.ArrowForward // (ترجیحاً ArrowForward برای جلو رفتن به جای Back)
    val actionClick = if (sentence.hasVoice) playVoice else openSheet
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        onClick = openSheet
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.End
            ) {
                LanguageAwareText(
                    modifier = Modifier.fillMaxWidth(),
                    text = sentence.fromText,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                LanguageAwareText(
                    modifier = Modifier.fillMaxWidth(),
                    text = sentence.toText,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .size(64.dp),
//                        .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(20.dp)),
                    onClick = actionClick
                ) {
                    Icon(actionIcon, contentDescription = "Play", Modifier.size(28.dp))
                }
            }
        }
    }
}

@Composable
fun SentenceShimmerList() {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        repeat(6) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(brush, RoundedCornerShape(20.dp))
            )
        }
    }
}