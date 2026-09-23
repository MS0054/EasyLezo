package am.mojtaba.armengo.ui.screen.sentence

import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.ui.component.LanguageAwareText
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                SentencesList(uiState.allWords, uiState.sentences, onSentenceClick, onPlayVoice)
            }
        }
    }
}

@Composable
fun SentencesList(
    words: List<Word>,
    sentences: List<Sentence>,
    onSentenceClick: (Sentence) -> Unit,
    onPlayVoice: (String) -> Unit,
) {
    Log.i("FGFGFG",words.toString())

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
                    words ,
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
fun SentenceItem(
    sentence: Sentence,
    allWords: List<Word>, // لیست کل لغات جهت Map کردن آیدی‌ها به دیتای واقعی
    openSheet: () -> Unit,
    playVoice: (String) -> Unit
) {
    // کلمه انتخابی فعلی کاربر
    var selectedWord by remember(sentence) { mutableStateOf<Word?>(null) }
    var isExpanded by remember { mutableStateOf(false) }

    val activeAlternative = sentence.alternatives.firstOrNull()

    // ۱. پیدا کردن کلمه اولیه (پیش‌فرض) از لیست allWords بر اساس slotKey
    val defaultWord = remember(activeAlternative, allWords) {
        if (activeAlternative != null) {
            allWords.find { it.fromText.equals(activeAlternative.slotKey, ignoreCase = true) }
        } else null
    }

    // ۲. جایگزین کردن کلمه انتخابی در متن انگلیسی اصلی
    val currentFromText = remember(selectedWord, sentence.fromText) {
        if (selectedWord != null && activeAlternative != null) {
            sentence.fromText.replace(activeAlternative.slotKey, selectedWord!!.fromText, ignoreCase = true)
        } else {
            sentence.fromText
        }
    }

    // ۳. جایگزین کردن کلمه ارمنی جدید به جای کلمه ارمنی قبلی در متن معنی/تلفظ
    val currentToText = remember(selectedWord, sentence.toText, defaultWord) {
        if (selectedWord != null && defaultWord != null && defaultWord.toText.isNotEmpty()) {
            sentence.toText.replace(defaultWord.toText, selectedWord!!.toText, ignoreCase = true)
        } else {
            sentence.toText
        }
    }

    val currentVoiceUrl = remember(selectedWord, sentence.voiceUrl) {
        selectedWord?.voiceUrl?.ifEmpty { sentence.voiceUrl } ?: sentence.voiceUrl
    }

    val actionIcon = if (sentence.hasVoice) Icons.Rounded.PlayArrow else Icons.Rounded.ArrowForward
    val actionClick = {
        if (sentence.hasVoice) playVoice(currentVoiceUrl) else openSheet()
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (sentence.alternatives.isNotEmpty()) {
                    isExpanded = !isExpanded
                } else {
                    openSheet()
                }
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // مشخص کردن کلمه قابل تغییر با زیرخط و رنگ متفاوت
                    val annotatedFromText = buildAnnotatedString {
                        if (activeAlternative != null) {
                            val activeWordText = selectedWord?.fromText ?: activeAlternative.slotKey
                            val parts = currentFromText.split(Regex("(?i)\\b$activeWordText\\b"))

                            if (parts.size > 1) {
                                append(parts[0])
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append(activeWordText)
                                }
                                append(parts[1])
                            } else {
                                append(currentFromText)
                            }
                        } else {
                            append(currentFromText)
                        }
                    }

                    Text(
                        text = annotatedFromText,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = currentToText,
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
                        modifier = Modifier.size(64.dp),
                        onClick = actionClick
                    ) {
                        Icon(actionIcon, contentDescription = "Play", modifier = Modifier.size(28.dp))
                    }
                }
            }

            // نمایش چیپ‌های افقی
            AnimatedVisibility(visible = isExpanded && activeAlternative != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val options = activeAlternative?.wordIds?.mapNotNull { id ->
                        allWords.find { it.id == id }
                    } ?: emptyList()

                    options.forEach { word ->
                        FilterChip(
                            selected = selectedWord?.id == word.id,
                            onClick = { selectedWord = word },
                            label = { Text(text = word.fromText) },
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
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