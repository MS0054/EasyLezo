package am.mojtaba.armengo.ui.screen.auth

import am.mojtaba.armengo.core.domain.usecase.auth.SignInUseCase
import am.mojtaba.armengo.core.domain.usecase.auth.SignInWithGoogleUseCase
import am.mojtaba.armengo.core.domain.usecase.auth.SignOutUseCase
import am.mojtaba.armengo.core.domain.usecase.auth.SignUpUseCase
import am.mojtaba.armengo.ui.UiEvent
import am.mojtaba.armengo.core.util.ErrorMessageProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val errorMessageProvider: ErrorMessageProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun signUp(email: String, password: String, displayName: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            signUpUseCase(email, password, displayName)
                .onSuccess {
                    _uiState.update { state -> state.copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure { throwable ->
                    _uiState.update { state -> state.copy(isLoading = false) }
                    _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
                }
        }
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            signInUseCase(email, password)
                .onSuccess {
                    _uiState.update { state -> state.copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure { throwable ->
                    _uiState.update { state -> state.copy(isLoading = false) }
                    _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
                }
        }
    }

    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            signInWithGoogleUseCase(idToken)
                .onSuccess {
                    _uiState.update { state -> state.copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure { throwable ->
                    _uiState.update { state -> state.copy(isLoading = false) }
                    _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            _uiState.update { AuthUiState() }
        }
    }
}