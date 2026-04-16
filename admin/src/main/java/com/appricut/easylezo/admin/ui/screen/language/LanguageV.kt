package com.appricut.easylezo.admin.ui.screen.language

import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.admin.ui.UiState
import com.appricut.easylezo.admin.ui.screen.BaseViewModel
import com.appricut.easylezo.admin.ui.toError
import com.appricut.easylezo.admin.ui.toLoading
import com.appricut.easylezo.admin.ui.toSuccess
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.usecase.category.AddLanguageUseCase
import com.appricut.easylezo.core.domain.usecase.category.DeleteLanguageUseCase
import com.appricut.easylezo.core.domain.usecase.category.SortLanguageUseCase
import com.appricut.easylezo.core.domain.usecase.category.UpdateLanguageUseCase
import com.appricut.easylezo.core.domain.usecase.language.GetLanguagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageV @Inject constructor(
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val addLanguageUseCase: AddLanguageUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val deleteLanguageUseCase: DeleteLanguageUseCase,
    private val sortLanguageUseCase: SortLanguageUseCase
) : BaseViewModel() {

    private val _languageUiState = MutableStateFlow(UiState<List<Language>>(isLoading = true))
    val languageUiState: StateFlow<UiState<List<Language>>> = _languageUiState.asStateFlow()

    init {
        getLanguages()
    }


    fun getLanguages() {
        viewModelScope.launch {
            getLanguagesUseCase()
                .onStart { _languageUiState.update { it.toLoading() } }
                .catch { e -> _languageUiState.update { it.toError(e.message ?: "Unknown") } }
                .collect { languages -> _languageUiState.update { it.toSuccess(languages) } }
        }
    }

    fun addLanguage(language: Language) {
        launchWithEvent(
            action = { addLanguageUseCase(language) },
            successMessage = "Added"
        )
    }

    fun updateLanguage(language: Language) {
        launchWithEvent(
            action = { updateLanguageUseCase(language) },
            successMessage = "Updated"
        )
    }

    fun sortLanguage(sorted: List<Language>) {
        launchWithEvent(
            action = { sortLanguageUseCase(sorted) },
            successMessage = "Sorted"
        )
    }

    fun deleteLanguage(language: Language) {
        launchWithEvent(
            action = { deleteLanguageUseCase(language) },
            successMessage = "Deleted"
        )
    }

}