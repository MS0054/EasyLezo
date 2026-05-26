package am.mojtaba.armengo.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.core.domain.model.UpdateResult
import am.mojtaba.armengo.core.domain.usecase.category.SyncCategoryFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.language.SyncLanguageFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.CheckUpdateUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.SyncMetadataUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SyncSentenceFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.user.DecideUserRoleUseCase
import am.mojtaba.armengo.ui.Screen
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
    private val syncLanguageFromServerUseCase: SyncLanguageFromServerUseCase,
    private val syncCategoryFromServerUseCase: SyncCategoryFromServerUseCase,
    private val decideUserRoleUseCase: DecideUserRoleUseCase,
    private val syncSentenceFromServerUseCase: SyncSentenceFromServerUseCase,
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
                    async { syncCategoryFromServerUseCase() },
                    async { syncLanguageFromServerUseCase() },
                    async { syncSentenceFromServerUseCase() }
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