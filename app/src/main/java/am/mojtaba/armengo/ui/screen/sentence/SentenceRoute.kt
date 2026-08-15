package am.mojtaba.armengo.ui.screen.sentence

import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.ui.UiEvent
import am.mojtaba.armengo.ui.screen.sentence.sheet.ShowSentenceSheet
import am.mojtaba.armengo.ui.screen.sentence.sheet.ShowWordSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
@Composable
fun SentenceRoute(
    snackBarHostState: SnackbarHostState,
    onBack: () -> Unit,
    viewModel: SentenceViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showSentenceSheet by remember { mutableStateOf(false) }
    var showWordSheet by remember { mutableStateOf(false) }
    var currentSentence by remember { mutableStateOf<Sentence?>(null) }
    var currentWord by remember { mutableStateOf<Word?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    currentSentence?.let { sentence ->
        if (showSentenceSheet) {
            ShowSentenceSheet(
                sentence = sentence,
                onDismiss = { showSentenceSheet = false },
                onPlay = viewModel::playVoice
            )
        }
    }

    currentWord?.let { word ->
        if (showWordSheet) {
            ShowWordSheet(
                word = word,
                onDismiss = { showWordSheet = false },
                onPlay = viewModel::playVoice
            )
        }
    }

    SentenceScreen(
        uiState = uiState,
        onSentenceClick = {
            currentSentence = it
            showSentenceSheet = true
        },
        onWordClick = {
            currentWord = it
            showWordSheet = true
        },
        onPlayVoice = viewModel::playVoice,
        onBack = {
            viewModel.stopVoice()
            onBack()
        }
    )
}