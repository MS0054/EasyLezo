package am.mojtaba.armengo.admin.ui.screen.user

sealed class AuthUiState {
    object Idle: AuthUiState()
    object Loading: AuthUiState()
    data class Success(val uid: String): AuthUiState()
    data class Error(val message: String): AuthUiState()
}