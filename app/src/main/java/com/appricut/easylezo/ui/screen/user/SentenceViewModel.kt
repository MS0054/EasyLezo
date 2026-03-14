package com.appricut.easylezo.ui.screen.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.domain.model.Sentence
import com.appricut.easylezo.domain.usecase.sentence.GetSentencesUseCase
import com.appricut.easylezo.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SentenceViewModel @Inject constructor(
    private val getSentencesUseCase: GetSentencesUseCase

) : ViewModel() {

    private val _sentenceUiState = MutableStateFlow(UiState<List<Sentence>>())
    val sentenceUiState: StateFlow<UiState<List<Sentence>>> = _sentenceUiState.asStateFlow()

    init {
    }
    fun getSentences(categoryId: String) {
        viewModelScope.launch {
            getSentencesUseCase(categoryId)
                .onStart {
                    _sentenceUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _sentenceUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { sentences ->
                    _sentenceUiState.value = UiState(data = sentences)
                }
        }
    }


}