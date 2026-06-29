package am.mojtaba.armengo.ui.screen.auth

import am.mojtaba.armengo.core.data.datastore.enums.UserRole
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.core.domain.usecase.auth.GetUserRoleUseCase
import am.mojtaba.armengo.core.domain.usecase.auth.SignInUseCase
import am.mojtaba.armengo.core.domain.usecase.auth.SignOutUseCase
import am.mojtaba.armengo.core.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase
): ViewModel() {



    private val _authUiState = MutableStateFlow<AuthUiState>(
        AuthUiState.Idle)
    val authUiState: StateFlow<AuthUiState> = _authUiState.asStateFlow()

    private val _isAdmin = MutableStateFlow<UserRole?>(null)
    val isAdmin: StateFlow<UserRole?> = _isAdmin.asStateFlow()

    fun signUp(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _authUiState.value = AuthUiState.Loading
            signUpUseCase(email, password, displayName)
                .onSuccess { uid -> _authUiState.value = AuthUiState.Success(uid) }
                .onFailure { t -> _authUiState.value = AuthUiState.Error(t.message ?: "Unknown Error") }
        }
    }
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authUiState.value = AuthUiState.Loading
            signInUseCase(email, password)
                .onSuccess { uid -> _authUiState.value = AuthUiState.Success(uid) }
                .onFailure { t -> _authUiState.value = AuthUiState.Error(t.message ?: "Unknown Error") }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            _authUiState.value = AuthUiState.Idle
        }
    }


}


