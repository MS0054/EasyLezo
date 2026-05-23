package am.mojtaba.armengo.admin.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.Screen
import am.mojtaba.armengo.core.data.datastore.enums.UserRole
import am.mojtaba.armengo.core.domain.usecase.category.SyncCategoriesUseCase
import am.mojtaba.armengo.core.domain.usecase.category.SyncUsersUseCase
import am.mojtaba.armengo.core.domain.usecase.language.SyncLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.SyncMetadataUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SyncSentencesUseCase
import am.mojtaba.armengo.core.domain.usecase.user.DecideUserRoleUseCase
import android.util.Log
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
                Log.i("MOJI6",e.toString())
                // اینجا می‌توانید خطای شبکه یا دیتابیس را مدیریت کنید
                // مثلا نمایش یک پیام خطا یا تلاش مجدد
            } finally {
                _isLoading.value = false // پایان لودینگ در هر شرایطی
            }
        }
    }
}