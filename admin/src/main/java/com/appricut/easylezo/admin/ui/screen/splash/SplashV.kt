package com.appricut.easylezo.admin.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.admin.ui.Screen
import com.appricut.easylezo.core.data.datastore.enums.UserRole
import com.appricut.easylezo.core.domain.usecase.category.SyncCategoriesUseCase
import com.appricut.easylezo.core.domain.usecase.category.SyncUsersUseCase
import com.appricut.easylezo.core.domain.usecase.language.SyncLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.metadata.SyncMetadataUseCase
import com.appricut.easylezo.core.domain.usecase.sentence.SyncSentencesUseCase
import com.appricut.easylezo.core.domain.usecase.user.DecideUserRoleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashV @Inject constructor(
    private val decideUserRoleUseCase: DecideUserRoleUseCase,
    private val syncLanguagesUseCase: SyncLanguagesUseCase,
    private val syncCategoriesUseCase: SyncCategoriesUseCase,
    private val syncMetadataUseCase: SyncMetadataUseCase,
    private val syncSentencesUseCase: SyncSentencesUseCase,
    private val syncUsersUseCase: SyncUsersUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _screen = MutableStateFlow<Screen?>(null)
    val screen = _screen.asStateFlow()

    fun start(isForce: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true // شروع لودینگ

            try {

                val role = decideUserRoleUseCase()
                _screen.value = when (role) {
                    UserRole.ADMIN -> {
                        syncMetadataUseCase()
                        syncCategoriesUseCase(isForce)
                        syncSentencesUseCase(isForce)
                        syncLanguagesUseCase(isForce)
                        syncUsersUseCase(100)
                        Screen.Category
                    }
                    UserRole.USER -> Screen.Auth
                }
            } catch (e: Exception) {
                // اینجا می‌توانید خطای شبکه یا دیتابیس را مدیریت کنید
                // مثلا نمایش یک پیام خطا یا تلاش مجدد
            } finally {
                _isLoading.value = false // پایان لودینگ در هر شرایطی
            }
        }
    }
}