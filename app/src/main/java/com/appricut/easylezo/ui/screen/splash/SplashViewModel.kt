package com.appricut.easylezo.ui.screen.splash

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.data.datastore.enums.UserRole
import com.appricut.easylezo.domain.usecase.category.SyncCategoriesUseCase
import com.appricut.easylezo.domain.usecase.language.SyncLanguagesUseCase
import com.appricut.easylezo.domain.usecase.metadata.SyncMetadataUseCase
import com.appricut.easylezo.domain.usecase.sentence.SyncSentencesUseCase
import com.appricut.easylezo.domain.usecase.user.DecideUserRoleUseCase
import com.appricut.easylezo.domain.usecase.user.SyncUserUseCase
import com.appricut.easylezo.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val syncMetadataUseCase: SyncMetadataUseCase,
    private val syncLanguagesUseCase: SyncLanguagesUseCase,
    private val syncCategoriesUseCase: SyncCategoriesUseCase,
    private val decideUserRoleUseCase: DecideUserRoleUseCase,
    private val syncSentencesUseCase: SyncSentencesUseCase
) : ViewModel() {


    private val _screen = MutableStateFlow<Screen?>(null)
    val screen = _screen.asStateFlow()

    fun start() {
        viewModelScope.launch {
            runBlocking { syncMetadataUseCase() }
            syncCategoriesUseCase()
            syncLanguagesUseCase()
            syncSentencesUseCase()
            val role = decideUserRoleUseCase()
            _screen.value = when (role) {
                UserRole.ADMIN -> Screen.Admin
                UserRole.USER -> Screen.Category
            }

        }
    }
}