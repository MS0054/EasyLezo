package am.mojtaba.armengo.ui.screen.sentence

import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.Word

data class SentenceUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val words: List<Word> = emptyList(),
    val sentences: List<Sentence> = emptyList()
)