package am.mojtaba.armengo.admin.ui.screen.sentence

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.usecase.sentence.AddSentenceUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.DeleteSentenceUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.DownloadVoiceOfSentencesUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.ObserveUnSyncedSentenceUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.UpdateSentenceUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.GetSentencesUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SyncSentenceFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SyncSentenceToServerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SentenceV @Inject constructor(
    private val getSentencesUseCase: GetSentencesUseCase,
    private val addSentenceUseCase: AddSentenceUseCase,
    private val updateSentenceUseCase: UpdateSentenceUseCase,
    private val deleteSentenceUseCase: DeleteSentenceUseCase,
    private val syncSentenceFromServerUseCase: SyncSentenceFromServerUseCase,
    private val syncSentenceToServerUseCase: SyncSentenceToServerUseCase,
    private val observeUnSyncedSentenceUseCase: ObserveUnSyncedSentenceUseCase,
    private val downloadVoiceOfSentencesUseCase: DownloadVoiceOfSentencesUseCase,

) : BaseViewModel() {

    private val _sentencesUiState = MutableStateFlow(UiState<List<Sentence>>())
    val sentencesUiState: StateFlow<UiState<List<Sentence>>> = _sentencesUiState.asStateFlow()
    private val _unsyncedSentenceState = MutableStateFlow(false)
    val unsyncedSentenceState: StateFlow<Boolean> = _unsyncedSentenceState.asStateFlow()


    init {
        observeSentences()
        observeSyncStatus()
    }

    fun observeSentences() {
        viewModelScope.launch {
            getSentencesUseCase()
                .onStart { _sentencesUiState.value = UiState(isLoading = true) }
                .catch { e ->
                    _sentencesUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { sentences ->
                    _sentencesUiState.value = UiState(data = sentences)
                    observeSyncStatus()
                }
        }
    }


    fun observeSyncStatus() {
        viewModelScope.launch {
            observeUnSyncedSentenceUseCase().collect { isSyncNeeded ->
                isSyncNeeded(isSyncNeeded)
                _unsyncedSentenceState.value = isSyncNeeded
            }
        }
    }

    fun syncSentenceToServer(workerTag: String = "sync_sentence") {
        launchSyncWithEvent(
            action = { syncSentenceToServerUseCase(workerTag) },
            workerTag = workerTag,
            successMessage = "Sentence Synced To Server"
        )
    }

    fun rejectSentenceChanges() {
        launchWithEvent(
            action = { syncSentenceFromServerUseCase(true) },
            successMessage = "Rejected Changes"
        )
    }

    fun addSentence(sentence: Sentence) {
        launchWithEvent(
            action = { addSentenceUseCase(sentence) },
            successMessage = "Added"
        )
    }

    fun updateSentence(sentence: Sentence) {
        launchWithEvent(
            action = { updateSentenceUseCase(sentence) },
            successMessage = "Updated"
        )
    }

    fun deleteSentence(id: String) {
        launchWithEvent(
            action = { deleteSentenceUseCase(id) },
            successMessage = "Deleted"
        )
    }

    suspend fun downloadVoices(sentences: List<Sentence>): Boolean {
        return try {
            downloadVoiceOfSentencesUseCase(sentences)
            true
        } catch (e: Exception) {
            false
        }
    }

}