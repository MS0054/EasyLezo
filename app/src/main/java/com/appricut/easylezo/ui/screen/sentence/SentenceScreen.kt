package com.appricut.easylezo.ui.screen.sentence
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.ui.component.LanguageAwareText
import com.appricut.easylezo.ui.screen.sentence.sheet.ShowSentenceSheet
import com.appricut.easylezo.ui.screen.sentence.SentenceViewModel


/**
 * ساختار ساده‌تر برای UiState
 */

@Composable
fun SentenceListScreen(
    categoryId: String,
    categoryName: String,
    sentenceViewModel: SentenceViewModel
) {
    val sentenceUiState by sentenceViewModel.sentenceUiState.collectAsState()

    var showSentenceSheet by remember { mutableStateOf(false) }
    var currentSentence by remember { mutableStateOf<Sentence?>(null) }

    LaunchedEffect(categoryId) {
        sentenceViewModel.getSentences(categoryId)
    }


    if (showSentenceSheet) {
        ShowSentenceSheet(
            sentence = currentSentence,
            onDismiss = { showSentenceSheet = false },
            onPlay = { /* منطق صوت */ }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(categoryName, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        when {
            sentenceUiState.isLoading -> {
                SentenceShimmerList()
            }
            sentenceUiState.error != null -> {
                Text("Error: ${sentenceUiState.error}", color = MaterialTheme.colorScheme.error)
            }

//            sentenceUiState.data  -> {
//                Text("No sentences found", modifier = Modifier.padding(top = 50.dp))
//            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(sentenceUiState.data ?: emptyList()) { sentence ->
                        SentenceItem(
                            sentence = sentence,
                            onClick = {
                                currentSentence = sentence
                                showSentenceSheet = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SentenceItem(sentence: Sentence, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
                    .size(90.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(topStart = 30.dp, bottomStart = 25.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(20.dp)),
                    onClick = onClick
                ) {
                    Icon(Icons.Rounded.PlayArrow, contentDescription = "Play")
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