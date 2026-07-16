package am.mojtaba.armengo.admin.ui.screen.splash

import am.mojtaba.armengo.admin.ui.Screen
import am.mojtaba.armengo.core.data.datastore.enums.UserRole
import am.mojtaba.armengo.core.domain.usecase.category.SyncCategoryFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.user.SyncUsersUseCase
import am.mojtaba.armengo.core.domain.usecase.language.SyncLanguageFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.SyncMetadataUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SyncSentenceFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.auth.GetUserRoleUseCase
import am.mojtaba.armengo.core.domain.usecase.word.SyncWordFromServerUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashV @Inject constructor(
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val syncLanguageFromServerUseCase: SyncLanguageFromServerUseCase,
    private val syncCategoryFromServerUseCase: SyncCategoryFromServerUseCase,
    private val syncMetadataUseCase: SyncMetadataUseCase,
    private val syncSentenceFromServerUseCase: SyncSentenceFromServerUseCase,
    private val syncWordFromServerUseCase: SyncWordFromServerUseCase,
    private val syncUsersUseCase: SyncUsersUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _screen = MutableStateFlow<Screen?>(null)
    val screen = _screen.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        start()
    }

    fun start(isForce: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            // ۱. بررسی نقش کاربر (فرض می‌کنیم این یوزکیس مقدار خام UserRole برمی‌گرداند یا Result. اگر Result است، مثل پایین هندل شود)
            val role = getUserRoleUseCase()

            if (role == UserRole.USER) {
                _screen.value = Screen.Auth
                _isLoading.value = false
                return@launch
            }

            // ۲. اگر کاربر ADMIN بود، فرآیند سینک آغاز می‌شود:
            // ابتدا متادیتا را سینک می‌کنیم و خروجی Result آن را می‌گیریم
            val metadataResult = syncMetadataUseCase()

            if (metadataResult.isFailure) {
                _errorMessage.value = metadataResult.exceptionOrNull()?.message ?: "خطا در دریافت متادیتا"
                _isLoading.value = false
                return@launch
            }

            // ۳. اجرای موازی سایر سینک‌ها به صورت async
            val syncTasks = listOf(
                async { syncCategoryFromServerUseCase(isForce) },
                async { syncSentenceFromServerUseCase(isForce) },
                async { syncWordFromServerUseCase(isForce) },
                async { syncLanguageFromServerUseCase(isForce) },
                async { syncUsersUseCase(100) }
            )

            // منتظر می‌مانیم تا همه تسک‌ها تمام شوند و لیستی از Result<Unit> دریافت می‌کنیم
            val results: List<Result<Unit>> = syncTasks.awaitAll()

            // ۴. بررسی اینکه آیا همه سینک‌ها موفق بوده‌اند یا خیر
            val firstFailure = results.firstOrNull { it.isFailure }

            if (firstFailure != null) {
                // اگر حتی یکی از سینک‌ها با خطا مواجه شده باشد
                _errorMessage.value = firstFailure.exceptionOrNull()?.message ?: "خطا در همگام‌سازی داده‌ها"
            } else {
                // اگر همه با موفقیت سبز شدند
                _screen.value = Screen.Category
            }

            _isLoading.value = false
        }
    }
}