package com.appricut.easylezo.admin.ui.screen.sentence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.admin.ui.UiEvent
import com.appricut.easylezo.admin.ui.UiState
import com.appricut.easylezo.admin.ui.screen.BaseViewModel
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.usecase.category.AddSentenceUseCase
import com.appricut.easylezo.core.domain.usecase.category.DeleteSentenceUseCase
import com.appricut.easylezo.core.domain.usecase.category.DownloadVoiceOfSentencesUseCase
import com.appricut.easylezo.core.domain.usecase.category.SortSentenceUseCase
import com.appricut.easylezo.core.domain.usecase.category.UpdateSentenceUseCase
import com.appricut.easylezo.core.domain.usecase.sentence.GetSentencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.s
import javax.inject.Inject

@HiltViewModel
class SentenceV @Inject constructor(
    private val getSentencesUseCase: GetSentencesUseCase,
    private val addSentenceUseCase: AddSentenceUseCase,
    private val updateSentenceUseCase: UpdateSentenceUseCase,
    private val sortSentencesUseCase: SortSentenceUseCase,
    private val deleteSentenceUseCase: DeleteSentenceUseCase,
    private val downloadVoiceOfSentencesUseCase: DownloadVoiceOfSentencesUseCase

) : BaseViewModel() {


    private val _sentenceUiState = MutableStateFlow(UiState<List<Sentence>>())
    val sentenceUiState: StateFlow<UiState<List<Sentence>>> = _sentenceUiState.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()




    fun getSentences(categoryId: String) {
        // save category id for bottom sheets
        _selectedCategoryId.value = categoryId
        viewModelScope.launch {
            getSentencesUseCase(categoryId)
                .onStart {
                    _sentenceUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _sentenceUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { sentences ->
//                    downloadVoices(sentences)
                    _sentenceUiState.value = UiState(data = sentences)
                }
        }
    }

    suspend fun downloadVoices(sentences: List<Sentence>): Boolean {
        return try {
            downloadVoiceOfSentencesUseCase(sentences)
            true
        } catch (e: Exception) {
            false
        }
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

    fun sortSentences(sorted: List<Sentence>)  {
        launchWithEvent(
            action = { sortSentencesUseCase(sorted) },
            successMessage = "Sorted"
        )
    }

    fun deleteSentence(sentence: Sentence) {
        launchWithEvent(
            action = { deleteSentenceUseCase(sentence) },
            successMessage = "Deleted"
        )
    }

}