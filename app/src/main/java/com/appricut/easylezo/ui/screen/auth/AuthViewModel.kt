package com.appricut.easylezo.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signUp(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val res = authRepo.signUpWithEmail(email, password, displayName)
            if (res.isSuccess) _uiState.value = AuthUiState.Success(res.getOrNull()!!)
            else _uiState.value = AuthUiState.Error(res.exceptionOrNull()?.message ?: "Error")
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val res = authRepo.signInWithEmail(email, password)
            if (res.isSuccess) _uiState.value = AuthUiState.Success(res.getOrNull()!!)
            else _uiState.value = AuthUiState.Error(res.exceptionOrNull()?.message ?: "Error")
        }
    }

    fun signOut() {
        authRepo.signOut()
        _uiState.value = AuthUiState.Idle
    }
}

sealed class AuthUiState {
    object Idle: AuthUiState()
    object Loading: AuthUiState()
    data class Success(val uid: String): AuthUiState()
    data class Error(val message: String): AuthUiState()
}
