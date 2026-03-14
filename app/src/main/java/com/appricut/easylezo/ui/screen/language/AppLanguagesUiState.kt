package com.appricut.easylezo.ui.screen.language

import com.appricut.easylezo.domain.model.AppLanguages
import com.appricut.easylezo.ui.screen.auth.AuthUiState

data class AppLanguagesUiState(
    val success: AppLanguages? = null,
    val loading: Boolean = false,
    val error: String? = null
)
