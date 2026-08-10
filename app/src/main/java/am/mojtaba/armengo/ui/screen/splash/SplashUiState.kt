package am.mojtaba.armengo.ui.screen.splash

import am.mojtaba.armengo.core.domain.model.UpdateResult

data class SplashUiState(
    val updateStatus: UpdateStatus = UpdateStatus.Idle,
    val isLoading: Boolean = false
)

sealed interface UpdateStatus {
    data object Idle : UpdateStatus
    data class Success(val updateResult: UpdateResult) : UpdateStatus
    data object Error : UpdateStatus
}