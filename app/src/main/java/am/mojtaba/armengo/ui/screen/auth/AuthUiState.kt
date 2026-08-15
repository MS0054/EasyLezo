package am.mojtaba.armengo.ui.screen.auth

import am.mojtaba.armengo.core.data.datastore.enums.UserRole

data class AuthUiState(
    val isLoading: Boolean = false,
    val userRole: UserRole? = null
)