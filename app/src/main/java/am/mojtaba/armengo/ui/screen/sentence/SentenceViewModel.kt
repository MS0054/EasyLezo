package am.mojtaba.armengo.ui.screen.sentence

import am.mojtaba.armengo.core.util.AudioHelper
import am.mojtaba.armengo.core.domain.usecase.sentence.GetCategorySentencesUseCase
import am.mojtaba.armengo.core.domain.usecase.word.GetWordsUseCase
import am.mojtaba.armengo.ui.UiEvent
import am.mojtaba.armengo.core.util.ErrorMessageProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SentenceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val audioManager: AudioHelper,
    private val getCategorySentencesUseCase: GetCategorySentencesUseCase,
    private val getWordsUseCase: GetWordsUseCase,
    private val errorMessageProvider: ErrorMessageProvider
) : ViewModel() {

    private val categoryId: String = checkNotNull(savedStateHandle["categoryId"])
    private val categoryName: String = checkNotNull(savedStateHandle["categoryName"])

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()


        val wordsFlow = getWordsUseCase(categoryId).catch { throwable ->
            _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
            emit(emptyList())
        }

        val sentencesFlow = getCategorySentencesUseCase(categoryId).catch { throwable ->
            _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
            emit(emptyList())
        }

        val uiState: StateFlow<SentenceUiState> = combine(wordsFlow,sentencesFlow) { words, sentences ->
                SentenceUiState(isLoading = false, title = categoryName, words = words, sentences = sentences)
            }
                .onStart {
                    emit(SentenceUiState(isLoading = true))
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = SentenceUiState(
                        isLoading = true
                    )
                )


    fun playVoice(voiceUrl: String) {
        audioManager.playAudio(voiceUrl)
    }
    fun stopVoice() {
        audioManager.stopAudio()
    }
    fun releaseVoice() {
        audioManager.release()
    }


}