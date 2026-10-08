package am.mojtaba.armengo.ui.screen.sentence

import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.model.ReportStatusType
import am.mojtaba.armengo.core.domain.repository.AppInfoProvider
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataReportMessagesUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataReportStatusTypesUseCase
import am.mojtaba.armengo.core.domain.usecase.report.AddReportUseCase
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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SentenceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val audioManager: AudioHelper,
    private val getCategorySentencesUseCase: GetCategorySentencesUseCase,
    private val getWordsUseCase: GetWordsUseCase,
    private val getMetadataReportMessagesUseCase: GetMetadataReportMessagesUseCase,
    private val getMetadataReportStatusTypesUseCase: GetMetadataReportStatusTypesUseCase,
    private val addReportUseCase: AddReportUseCase,
    private val appInfoProvider: AppInfoProvider,
    private val appLanguagesRepository: AppLanguagesRepository,
    private val errorMessageProvider: ErrorMessageProvider
) : ViewModel() {

    private val categoryId: String = checkNotNull(savedStateHandle["categoryId"])
    private val categoryName: String = checkNotNull(savedStateHandle["categoryName"])

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    val allWordsFlow = getWordsUseCase().catch { throwable ->
        _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
        emit(emptyList())
    }

    val wordsFlow = getWordsUseCase(categoryId).catch { throwable ->
        _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
        emit(emptyList())
    }

    val sentencesFlow = getCategorySentencesUseCase(categoryId).catch { throwable ->
        _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
        emit(emptyList())
    }

    val reportMessagesFlow = getMetadataReportMessagesUseCase().catch { throwable ->
        emit(emptyList())
    }

    val uiState: StateFlow<SentenceUiState> =
        combine(allWordsFlow, wordsFlow, sentencesFlow, reportMessagesFlow) { allWords, words, sentences, reportMessages ->
            SentenceUiState(
                isLoading = false,
                title = categoryName,
                allWords = allWords,
                words = words,
                sentences = sentences,
                reportMessages = reportMessages
            )
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

    fun sendReport(
        reportType: String,
        itemId: String,
        reportMessageKey: String,
        userComment: String
    ) {
        viewModelScope.launch {
            val appLanguage = appLanguagesRepository.observeAppLanguages().firstOrNull()?.app ?: ""
            val pendingStatus = getMetadataReportStatusTypesUseCase().firstOrNull()?.find { it.key.equals("PENDING", ignoreCase = true) }
                ?: ReportStatusType(key = "PENDING")
            val report = Report(
                id = UUID.randomUUID().toString(),
                reportType = reportType,
                reportMessageKey = reportMessageKey,
                reportStatusType = pendingStatus,
                itemId = itemId,
                userComment = userComment,
                appVersion = appInfoProvider.getVersionName(),
                deviceInfo = appInfoProvider.getDeviceInfo(),
                createdAt = System.currentTimeMillis(),
                userAppLanguage = appLanguage
            )
            addReportUseCase(report)
                .onSuccess {
                    _uiEvent.emit(UiEvent.ShowSnackbar("Report submitted successfully"))
                }
                .onFailure { e ->
                    _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(e)))
                }
        }
    }
}