package com.appricut.easylezo.ui.screen.splash

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.core.domain.model.UpdateResult
import com.appricut.easylezo.core.domain.usecase.category.SyncCategoriesUseCase
import com.appricut.easylezo.core.domain.usecase.language.SyncLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.metadata.CheckUpdateUseCase
import com.appricut.easylezo.core.domain.usecase.metadata.SyncMetadataUseCase
import com.appricut.easylezo.core.domain.usecase.sentence.SyncSentencesUseCase
import com.appricut.easylezo.core.domain.usecase.user.DecideUserRoleUseCase
import com.appricut.easylezo.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val syncMetadataUseCase: SyncMetadataUseCase,
    private val syncLanguagesUseCase: SyncLanguagesUseCase,
    private val syncCategoriesUseCase: SyncCategoriesUseCase,
    private val decideUserRoleUseCase: DecideUserRoleUseCase,
    private val syncSentencesUseCase: SyncSentencesUseCase,
    private val checkUpdateUseCase: CheckUpdateUseCase
) : ViewModel() {


    private val _updateState = MutableStateFlow<UpdateStatus>(UpdateStatus.Idle)
    val updateState = _updateState.asStateFlow()
    private val _screen = MutableStateFlow<Screen?>(null)
    val screen = _screen.asStateFlow()

    init {
        start()
    }


    fun start() {
        viewModelScope.launch {
            try {
                syncMetadataUseCase()
                checkAppUpdate()
//                syncCategoriesUseCase()
//                syncLanguagesUseCase()
//                syncSentencesUseCase()
                // اجرا به صورت همزمان برای صرفه‌جویی در زمان
                joinAll(
                    async { syncCategoriesUseCase() },
                    async { syncLanguagesUseCase() },
                    async { syncSentencesUseCase() }
                )
//                _screen.value = Screen.Category
            } catch (e: Exception) {
//                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkAppUpdate() {
        viewModelScope.launch {
            try {
                val updateInfo = checkUpdateUseCase()
                _updateState.value = UpdateStatus.Success(updateInfo)
            } catch (e: Exception) {
                _updateState.value = UpdateStatus.Error
            }
        }
    }
}


sealed class UpdateStatus {
    object Idle : UpdateStatus()
    data class Success(val updateResult: UpdateResult) : UpdateStatus()
    object Error : UpdateStatus()
}